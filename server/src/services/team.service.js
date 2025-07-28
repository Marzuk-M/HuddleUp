const admin = require('../firebase/admin');

/**
 * Search teams by name (case-insensitive)
 * @param {string} searchQuery - The search term
 * @param {string} userId - Current user ID
 * @returns {Array} Array of teams with membership state
 */
async function searchTeams(searchQuery, userId) {
  console.log(`Searching teams with query: '${searchQuery}', userId: ${userId}`);
  let teamsSnapshot;
  
  if (!searchQuery || !searchQuery.trim()) {
    console.log('Empty query - fetching all teams');
    // Return all teams when search query is empty
    teamsSnapshot = await admin.firestore()
      .collection('teams')
      .limit(50)
      .get();
    console.log(`Found ${teamsSnapshot.docs.length} teams in database`);
    
    // Auto-seed teams if none exist
    if (teamsSnapshot.docs.length === 0) {
      console.log('No teams found - auto-seeding sample teams');
      await autoSeedTeams();
      // Fetch teams again after seeding
      teamsSnapshot = await admin.firestore()
        .collection('teams')
        .limit(50)
        .get();
      console.log(`After seeding: Found ${teamsSnapshot.docs.length} teams`);
    }
  } else {
    const query = searchQuery.trim().toLowerCase();
    console.log(`Searching with query: '${query}'`);
    
    // Get all teams and filter by name or ID
    const allTeamsSnapshot = await admin.firestore()
      .collection('teams')
      .limit(50)
      .get();
    
    // Filter teams by name or ID (ID must start with query)
    const filteredDocs = allTeamsSnapshot.docs.filter(doc => {
      const teamData = doc.data();
      const teamName = teamData.name.toLowerCase();
      const teamId = doc.id.toLowerCase();
      
      return teamName.includes(query) || teamId.startsWith(query);
    });
    
    console.log(`Found ${filteredDocs.length} teams matching '${query}'`);
    
    teamsSnapshot = {
      docs: filteredDocs
    };
  }

  const teams = [];
  
  for (const doc of teamsSnapshot.docs) {
    const teamData = doc.data();
    
    // Get user's membership state for this team
    const membershipState = await getUserMembershipState(userId, doc.id);
    
    teams.push({
      id: doc.id,
      name: teamData.name,
      members: teamData.memberCount || 0,
      membershipState: membershipState
    });
  }

  return teams;
}

/**
 * Get user's membership state with a specific team
 * @param {string} userId - User ID
 * @param {string} teamId - Team ID
 * @returns {string} Membership state
 */
async function getUserMembershipState(userId, teamId) {
  try {
    const membershipDoc = await admin.firestore()
      .collection('team_memberships')
      .doc(`${userId}_${teamId}`)
      .get();

    if (!membershipDoc.exists) {
      return 'NOT_A_MEMBER';
    }

    const membershipData = membershipDoc.data();
    return membershipData.status || 'NOT_A_MEMBER';
  } catch (error) {
    console.error('Error getting membership state:', error);
    return 'NOT_A_MEMBER';
  }
}

/**
 * Join a team instantly
 * @param {string} userId - User ID
 * @param {string} teamId - Team ID
 * @returns {boolean} Success status
 */
async function sendJoinRequest(userId, teamId) {
  try {
    console.log(`Joining team: userId=${userId}, teamId=${teamId}`);
    
    // Check if team exists
    const teamDoc = await admin.firestore()
      .collection('teams')
      .doc(teamId)
      .get();

    if (!teamDoc.exists) {
      throw new Error('Team not found');
    }

    // Check if user already has a membership record
    const membershipRef = admin.firestore()
      .collection('team_memberships')
      .doc(`${userId}_${teamId}`);

    const membershipDoc = await membershipRef.get();

    if (membershipDoc.exists) {
      const currentStatus = membershipDoc.data().status;
      console.log(`Existing membership status: ${currentStatus}`);
      if (currentStatus === 'MEMBER') {
        throw new Error('Already a member of this team');
      }
      // If they have a REQUESTED status, upgrade it to MEMBER
      if (currentStatus === 'REQUESTED') {
        await membershipRef.update({
          status: 'MEMBER',
          joinedAt: Date.now()
        });
        console.log(`Upgraded request to membership for userId=${userId}, teamId=${teamId}`);
        return true;
      }
    }

    // Create membership record as MEMBER
    await membershipRef.set({
      userId,
      teamId,
      status: 'MEMBER',
      joinedAt: Date.now()
    });

    // Update team member count
    const teamRef = admin.firestore().collection('teams').doc(teamId);
    await teamRef.update({
      memberCount: admin.firestore.FieldValue.increment(1)
    });

    console.log(`Successfully joined team for userId=${userId}, teamId=${teamId}`);
    return true;
  } catch (error) {
    console.error('Error joining team:', error);
    throw error;
  }
}

/**
 * Cancel a join request
 * @param {string} userId - User ID
 * @param {string} teamId - Team ID
 * @returns {boolean} Success status
 */
async function unsendJoinRequest(userId, teamId) {
  try {
    const membershipRef = admin.firestore()
      .collection('team_memberships')
      .doc(`${userId}_${teamId}`);

    const membershipDoc = await membershipRef.get();

    if (!membershipDoc.exists) {
      throw new Error('No membership record found');
    }

    const membershipData = membershipDoc.data();
    if (membershipData.status !== 'REQUESTED') {
      throw new Error('No pending join request found');
    }

    await membershipRef.delete();
    return true;
  } catch (error) {
    console.error('Error canceling join request:', error);
    throw error;
  }
}

/**
 * Leave a team
 * @param {string} userId - User ID
 * @param {string} teamId - Team ID
 * @returns {boolean} Success status
 */
async function leaveTeam(userId, teamId) {
  try {
    const membershipRef = admin.firestore()
      .collection('team_memberships')
      .doc(`${userId}_${teamId}`);

    const membershipDoc = await membershipRef.get();

    if (!membershipDoc.exists) {
      throw new Error('No membership record found');
    }

    const membershipData = membershipDoc.data();
    if (membershipData.status !== 'MEMBER') {
      throw new Error('Not a member of this team');
    }

    // Delete membership record
    await membershipRef.delete();

    // Update team member count
    const teamRef = admin.firestore().collection('teams').doc(teamId);
    await teamRef.update({
      memberCount: admin.firestore.FieldValue.increment(-1)
    });

    return true;
  } catch (error) {
    console.error('Error leaving team:', error);
    throw error;
  }
}

/**
 * Get team details
 * @param {string} teamId - Team ID
 * @returns {Object} Team details
 */
async function getTeamDetails(teamId) {
  try {
    const teamDoc = await admin.firestore()
      .collection('teams')
      .doc(teamId)
      .get();

    if (!teamDoc.exists) {
      throw new Error('Team not found');
    }

    return {
      id: teamDoc.id,
      ...teamDoc.data()
    };
  } catch (error) {
    console.error('Error getting team details:', error);
    throw error;
  }
}

/**
 * Create a new team
 * @param {string} userId - Creator's user ID
 * @param {Object} teamData - Team data
 * @returns {string} Team ID
 */
async function createTeam(userId, teamData) {
  try {
    const { name, description } = teamData;

    if (!name || !name.trim()) {
      throw new Error('Team name is required');
    }

    // Check if team name already exists
    const existingTeam = await admin.firestore()
      .collection('teams')
      .where('name_lower', '==', name.toLowerCase())
      .limit(1)
      .get();

    if (!existingTeam.empty) {
      throw new Error('Team name already exists');
    }

    const teamRef = admin.firestore().collection('teams').doc();
    
    const newTeam = {
      name: name.trim(),
      name_lower: name.toLowerCase(),
      description: description || '',
      creatorId: userId,
      memberCount: 1,
      createdAt: Date.now(),
      updatedAt: Date.now()
    };

    await teamRef.set(newTeam);

    // Add creator as member
    const membershipRef = admin.firestore()
      .collection('team_memberships')
      .doc(`${userId}_${teamRef.id}`);

    await membershipRef.set({
      userId,
      teamId: teamRef.id,
      status: 'MEMBER',
      joinedAt: Date.now()
    });

    return teamRef.id;
  } catch (error) {
    console.error('Error creating team:', error);
    throw error;
  }
}

/**
 * Auto-seed teams if none exist
 */
async function autoSeedTeams() {
  const sampleTeams = [
    {
      name: "Basketball Warriors",
      description: "A competitive basketball team looking for dedicated players",
      memberCount: 8
    },
    {
      name: "Soccer Legends",
      description: "Casual soccer team for weekend games",
      memberCount: 12
    },
    {
      name: "Tennis Pros",
      description: "Advanced tennis players only",
      memberCount: 6
    },
    {
      name: "Volleyball Stars",
      description: "Recreational volleyball team",
      memberCount: 10
    },
    {
      name: "Swimming Dolphins",
      description: "Swimming club for all levels",
      memberCount: 15
    },
    {
      name: "Running Club",
      description: "Morning running group",
      memberCount: 20
    },
    {
      name: "Gym Buddies",
      description: "Fitness and workout partners",
      memberCount: 25
    },
    {
      name: "Yoga Masters",
      description: "Yoga and meditation group",
      memberCount: 18
    },
    {
      name: "Cycling Team",
      description: "Road cycling enthusiasts",
      memberCount: 14
    },
    {
      name: "Hiking Adventures",
      description: "Weekend hiking and outdoor activities",
      memberCount: 22
    }
  ];

  for (const team of sampleTeams) {
    const teamRef = admin.firestore().collection('teams').doc();
    
    await teamRef.set({
      name: team.name,
      name_lower: team.name.toLowerCase(),
      description: team.description,
      memberCount: team.memberCount,
      creatorId: 'system', // System-created teams
      createdAt: Date.now(),
      updatedAt: Date.now()
    });
    
    console.log(`Auto-created team: ${team.name}`);
  }
}

module.exports = {
  searchTeams,
  getUserMembershipState,
  sendJoinRequest,
  unsendJoinRequest,
  leaveTeam,
  getTeamDetails,
  createTeam
}; 