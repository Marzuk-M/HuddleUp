const admin = require('../firebase/admin');

/**
 * Get all games for a user's teams
 * @param {string} userId - The user's ID
 * @returns {Promise<Array>} Array of games
 */
async function getUserGames(userId) {
  try {
    console.log(`Getting games for user: ${userId}`);
    
    // First get user's team memberships
    const membershipsSnapshot = await admin.firestore()
      .collection('team_memberships')
      .where('userId', '==', userId)
      .where('status', '==', 'MEMBER')
      .get();
    
    const userTeamIds = membershipsSnapshot.docs.map(doc => doc.data().teamId);
    console.log(`User is member of teams: ${userTeamIds}`);
    
    if (userTeamIds.length === 0) {
      console.log('User is not a member of any teams');
      return [];
    }
    
    // Get games for user's teams
    const gamesSnapshot = await admin.firestore()
      .collection('games')
      .where('teamId', 'in', userTeamIds)
      .orderBy('date', 'asc')
      .get();
    
    const games = [];
    for (const doc of gamesSnapshot.docs) {
      const gameData = doc.data();
      const availability = await getGameAvailability(doc.id);
      
      games.push({
        id: doc.id,
        teamId: gameData.teamId,
        teamName: gameData.teamName,
        opponent: gameData.opponent,
        date: gameData.date,
        time: gameData.time,
        place: gameData.place,
        availability: availability
      });
    }
    
    console.log(`Found ${games.length} games for user`);
    return games;
  } catch (error) {
    console.error('Error getting user games:', error);
    throw error;
  }
}

/**
 * Get all games (for admin/overview purposes)
 * @returns {Promise<Array>} Array of all games
 */
async function getAllGames() {
  try {
    console.log('Getting all games');
    
    const gamesSnapshot = await admin.firestore()
      .collection('games')
      .orderBy('date', 'asc')
      .get();
    
    // Auto-seed games if none exist
    if (gamesSnapshot.docs.length === 0) {
      console.log('No games found - auto-seeding sample games');
      await autoSeedGames();
      // Fetch games again after seeding
      const newGamesSnapshot = await admin.firestore()
        .collection('games')
        .orderBy('date', 'asc')
        .get();
      console.log(`After seeding: Found ${newGamesSnapshot.docs.length} games`);
      
      const games = [];
      for (const doc of newGamesSnapshot.docs) {
        const gameData = doc.data();
        const availability = await getGameAvailability(doc.id);
        
        games.push({
          id: doc.id,
          teamId: gameData.teamId,
          teamName: gameData.teamName,
          opponent: gameData.opponent,
          date: gameData.date,
          time: gameData.time,
          place: gameData.place,
          availability: availability
        });
      }
      
      console.log(`Found ${games.length} total games`);
      return games;
    }
    
    const games = [];
    for (const doc of gamesSnapshot.docs) {
      const gameData = doc.data();
      const availability = await getGameAvailability(doc.id);
      
      games.push({
        id: doc.id,
        teamId: gameData.teamId,
        teamName: gameData.teamName,
        opponent: gameData.opponent,
        date: gameData.date,
        time: gameData.time,
        place: gameData.place,
        availability: availability
      });
    }
    
    console.log(`Found ${games.length} total games`);
    return games;
  } catch (error) {
    console.error('Error getting all games:', error);
    throw error;
  }
}

/**
 * Get game details by ID
 * @param {string} gameId - The game ID
 * @returns {Promise<Object>} Game details
 */
async function getGameDetails(gameId) {
  try {
    console.log(`Getting game details for: ${gameId}`);
    
    const gameDoc = await admin.firestore()
      .collection('games')
      .doc(gameId)
      .get();
    
    if (!gameDoc.exists) {
      throw new Error('Game not found');
    }
    
    const gameData = gameDoc.data();
    const availability = await getGameAvailability(gameId);
    
    return {
      id: gameDoc.id,
      teamId: gameData.teamId,
      teamName: gameData.teamName,
      opponent: gameData.opponent,
      date: gameData.date,
      time: gameData.time,
      place: gameData.place,
      availability: availability
    };
  } catch (error) {
    console.error('Error getting game details:', error);
    throw error;
  }
}

/**
 * Create a new game
 * @param {Object} gameData - Game data
 * @returns {Promise<string>} Created game ID
 */
async function createGame(gameData) {
  try {
    console.log('Creating new game:', gameData);
    
    const gameRef = await admin.firestore()
      .collection('games')
      .add({
        teamId: gameData.teamId,
        teamName: gameData.teamName,
        opponent: gameData.opponent,
        date: gameData.date,
        time: gameData.time,
        place: gameData.place,
        createdAt: Date.now(),
        updatedAt: Date.now()
      });
    
    console.log(`Created game with ID: ${gameRef.id}`);
    return gameRef.id;
  } catch (error) {
    console.error('Error creating game:', error);
    throw error;
  }
}

/**
 * Update game availability for a user
 * @param {string} gameId - The game ID
 * @param {string} userId - The user ID
 * @param {string} status - 'in', 'out', or 'maybe'
 * @returns {Promise<boolean>} Success status
 */
async function updateGameAvailability(gameId, userId, status) {
  try {
    console.log(`Updating availability: gameId=${gameId}, userId=${userId}, status=${status}`);
    
    const availabilityRef = admin.firestore()
      .collection('game_availability')
      .doc(`${gameId}_${userId}`);
    
    await availabilityRef.set({
      gameId: gameId,
      userId: userId,
      status: status,
      updatedAt: Date.now()
    });
    
    console.log(`Updated availability for user ${userId} in game ${gameId}`);
    return true;
  } catch (error) {
    console.error('Error updating game availability:', error);
    throw error;
  }
}

/**
 * Get availability for a specific game
 * @param {string} gameId - The game ID
 * @returns {Promise<Object>} Availability data
 */
async function getGameAvailability(gameId) {
  try {
    const availabilitySnapshot = await admin.firestore()
      .collection('game_availability')
      .where('gameId', '==', gameId)
      .get();
    
    const availability = {
      in: [],
      out: [],
      maybe: []
    };
    
    for (const doc of availabilitySnapshot.docs) {
      const data = doc.data();
      if (data.status === 'in') {
        availability.in.push(data.userId);
      } else if (data.status === 'out') {
        availability.out.push(data.userId);
      } else if (data.status === 'maybe') {
        availability.maybe.push(data.userId);
      }
    }
    
    return availability;
  } catch (error) {
    console.error('Error getting game availability:', error);
    return { in: [], out: [], maybe: [] };
  }
}

/**
 * Auto-seed sample games if none exist
 */
async function autoSeedGames() {
  try {
    const gamesSnapshot = await admin.firestore()
      .collection('games')
      .limit(1)
      .get();
    
    if (gamesSnapshot.docs.length === 0) {
      console.log('No games found - auto-seeding sample games');
      
      const sampleGames = [
        {
          teamId: 'team1',
          teamName: 'Demo FC',
          opponent: 'Test United',
          date: '2025-07-25',
          time: '7:30 PM',
          place: 'Paramount Fine Foods Centre'
        },
        {
          teamId: 'team2',
          teamName: 'Red Wolves',
          opponent: 'Blue Sharks',
          date: '2025-07-29',
          time: '6:00 PM',
          place: 'Iceland Arena'
        },
        {
          teamId: 'team1',
          teamName: 'Lions',
          opponent: 'Bears',
          date: '2025-07-31',
          time: '8:45 PM',
          place: 'Clarkson Arena'
        },
        {
          teamId: 'team3',
          teamName: 'Thunder',
          opponent: 'Lightning',
          date: '2025-08-02',
          time: '5:30 PM',
          place: 'Sports Complex'
        },
        {
          teamId: 'team1',
          teamName: 'Eagles',
          opponent: 'Falcons',
          date: '2025-08-05',
          time: '7:00 PM',
          place: 'Community Center'
        }
      ];
      
      for (const game of sampleGames) {
        await createGame(game);
      }
      
      console.log('Auto-seeded sample games');
    }
  } catch (error) {
    console.error('Error auto-seeding games:', error);
  }
}

module.exports = {
  getUserGames,
  getAllGames,
  getGameDetails,
  createGame,
  updateGameAvailability,
  getGameAvailability,
  autoSeedGames
}; 