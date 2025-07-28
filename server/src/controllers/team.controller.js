const teamService = require('../services/team.service');
const admin = require('../firebase/admin');

/**
 * Search teams by name
 * GET /api/teams/search?q=searchQuery
 */
exports.searchTeams = async (req, res) => {
  try {
    const { q: searchQuery } = req.query;
    const userId = req.user.uid; // From auth middleware

    if (!searchQuery || !searchQuery.trim()) {
      return res.status(200).json([]);
    }

    const teams = await teamService.searchTeams(searchQuery, userId);
    res.status(200).json(teams);
  } catch (error) {
    console.error('Error searching teams:', error);
    res.status(500).json({ error: 'Failed to search teams' });
  }
};

/**
 * Test endpoint for team search (no authentication required)
 * GET /api/teams/search/test?q=searchQuery
 */
exports.searchTeamsTest = async (req, res) => {
  try {
    const { q: searchQuery } = req.query;
    console.log(`Test endpoint called with query: '${searchQuery}'`);

    const teams = await teamService.searchTeams(searchQuery, 'test-user');
    console.log(`Returning ${teams.length} teams to client`);
    res.status(200).json(teams);
  } catch (error) {
    console.error('Error searching teams:', error);
    res.status(500).json({ error: 'Failed to search teams' });
  }
};

/**
 * Test endpoint for joining team instantly (no authentication required)
 * POST /api/teams/:teamId/join/test
 */
exports.sendJoinRequestTest = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = 'test-user';

    await teamService.sendJoinRequest(userId, teamId);
    res.status(200).json({ message: 'Successfully joined team' });
  } catch (error) {
    console.error('Error joining team:', error);
    res.status(500).json({ error: 'Failed to join team' });
  }
};

/**
 * Test endpoint for canceling join request (no authentication required)
 * DELETE /api/teams/:teamId/join/test
 */
exports.unsendJoinRequestTest = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = 'test-user';

    await teamService.unsendJoinRequest(userId, teamId);
    res.status(200).json({ message: 'Join request canceled successfully' });
  } catch (error) {
    console.error('Error canceling join request:', error);
    res.status(500).json({ error: 'Failed to cancel join request' });
  }
};

/**
 * Test endpoint for leaving team (no authentication required)
 * DELETE /api/teams/:teamId/leave/test
 */
exports.leaveTeamTest = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = 'test-user';

    await teamService.leaveTeam(userId, teamId);
    res.status(200).json({ message: 'Left team successfully' });
  } catch (error) {
    console.error('Error leaving team:', error);
    res.status(500).json({ error: 'Failed to leave team' });
  }
};

/**
 * Test endpoint for getting user's teams (no authentication required)
 * GET /api/teams/my-teams/test
 */
exports.getUserTeamsTest = async (req, res) => {
  try {
    const userId = 'test-user';

    // Get all teams where user is a member
    const membershipsSnapshot = await admin.firestore()
      .collection('team_memberships')
      .where('userId', '==', userId)
      .where('status', '==', 'MEMBER')
      .get();

    const teams = [];
    
    for (const doc of membershipsSnapshot.docs) {
      const membershipData = doc.data();
      const teamDetails = await teamService.getTeamDetails(membershipData.teamId);
      teams.push({
        id: teamDetails.id,
        name: teamDetails.name,
        members: teamDetails.memberCount || 0,
        membershipState: 'MEMBER'
      });
    }

    res.status(200).json(teams);
  } catch (error) {
    console.error('Error getting user teams:', error);
    res.status(500).json({ error: 'Failed to get user teams' });
  }
};

/**
 * Get team details
 * GET /api/teams/:teamId
 */
exports.getTeamDetails = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = req.user.uid;

    const teamDetails = await teamService.getTeamDetails(teamId);
    const membershipState = await teamService.getUserMembershipState(userId, teamId);

    res.status(200).json({
      ...teamDetails,
      membershipState
    });
  } catch (error) {
    console.error('Error getting team details:', error);
    if (error.message === 'Team not found') {
      res.status(404).json({ error: 'Team not found' });
    } else {
      res.status(500).json({ error: 'Failed to get team details' });
    }
  }
};

/**
 * Join a team instantly
 * POST /api/teams/:teamId/join
 */
exports.sendJoinRequest = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = req.user.uid;

    await teamService.sendJoinRequest(userId, teamId);
    res.status(200).json({ message: 'Successfully joined team' });
  } catch (error) {
    console.error('Error joining team:', error);
    
    if (error.message === 'Team not found') {
      res.status(404).json({ error: 'Team not found' });
    } else if (error.message === 'Already a member of this team') {
      res.status(400).json({ error: 'Already a member of this team' });
    } else {
      res.status(500).json({ error: 'Failed to join team' });
    }
  }
};

/**
 * Cancel join request
 * DELETE /api/teams/:teamId/join
 */
exports.unsendJoinRequest = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = req.user.uid;

    await teamService.unsendJoinRequest(userId, teamId);
    res.status(200).json({ message: 'Join request canceled successfully' });
  } catch (error) {
    console.error('Error canceling join request:', error);
    
    if (error.message === 'No membership record found') {
      res.status(404).json({ error: 'No membership record found' });
    } else if (error.message === 'No pending join request found') {
      res.status(400).json({ error: 'No pending join request found' });
    } else {
      res.status(500).json({ error: 'Failed to cancel join request' });
    }
  }
};

/**
 * Leave a team
 * DELETE /api/teams/:teamId/leave
 */
exports.leaveTeam = async (req, res) => {
  try {
    const { teamId } = req.params;
    const userId = req.user.uid;

    await teamService.leaveTeam(userId, teamId);
    res.status(200).json({ message: 'Left team successfully' });
  } catch (error) {
    console.error('Error leaving team:', error);
    
    if (error.message === 'No membership record found') {
      res.status(404).json({ error: 'No membership record found' });
    } else if (error.message === 'Not a member of this team') {
      res.status(400).json({ error: 'Not a member of this team' });
    } else {
      res.status(500).json({ error: 'Failed to leave team' });
    }
  }
};

/**
 * Create a new team
 * POST /api/teams
 */
exports.createTeam = async (req, res) => {
  try {
    const { name, description } = req.body;
    const userId = req.user.uid;

    if (!name || !name.trim()) {
      return res.status(400).json({ error: 'Team name is required' });
    }

    const teamId = await teamService.createTeam(userId, { name, description });
    res.status(201).json({ 
      message: 'Team created successfully', 
      teamId 
    });
  } catch (error) {
    console.error('Error creating team:', error);
    
    if (error.message === 'Team name already exists') {
      res.status(400).json({ error: 'Team name already exists' });
    } else {
      res.status(500).json({ error: 'Failed to create team' });
    }
  }
};

/**
 * Get user's teams
 * GET /api/teams/my-teams
 */
exports.getUserTeams = async (req, res) => {
  try {
    const userId = req.user.uid;

    // Get all teams where user is a member
    const membershipsSnapshot = await admin.firestore()
      .collection('team_memberships')
      .where('userId', '==', userId)
      .where('status', '==', 'MEMBER')
      .get();

    const teams = [];
    
    for (const doc of membershipsSnapshot.docs) {
      const membershipData = doc.data();
      const teamDetails = await teamService.getTeamDetails(membershipData.teamId);
      teams.push({
        ...teamDetails,
        membershipState: 'MEMBER'
      });
    }

    res.status(200).json(teams);
  } catch (error) {
    console.error('Error getting user teams:', error);
    res.status(500).json({ error: 'Failed to get user teams' });
  }
}; 