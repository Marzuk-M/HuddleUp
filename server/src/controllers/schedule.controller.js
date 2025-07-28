const scheduleService = require('../services/schedule.service');

/**
 * Get all games for the authenticated user
 * GET /api/schedule/my-games
 */
exports.getUserGames = async (req, res) => {
  try {
    const userId = req.user.uid;
    const games = await scheduleService.getUserGames(userId);
    res.status(200).json(games);
  } catch (error) {
    console.error('Error getting user games:', error);
    res.status(500).json({ error: 'Failed to get user games' });
  }
};

/**
 * Get all games (for admin/overview)
 * GET /api/schedule/games
 */
exports.getAllGames = async (req, res) => {
  try {
    const games = await scheduleService.getAllGames();
    res.status(200).json(games);
  } catch (error) {
    console.error('Error getting all games:', error);
    res.status(500).json({ error: 'Failed to get games' });
  }
};

/**
 * Get game details by ID
 * GET /api/schedule/games/:gameId
 */
exports.getGameDetails = async (req, res) => {
  try {
    const { gameId } = req.params;
    const game = await scheduleService.getGameDetails(gameId);
    res.status(200).json(game);
  } catch (error) {
    console.error('Error getting game details:', error);
    if (error.message === 'Game not found') {
      res.status(404).json({ error: 'Game not found' });
    } else {
      res.status(500).json({ error: 'Failed to get game details' });
    }
  }
};

/**
 * Create a new game
 * POST /api/schedule/games
 */
exports.createGame = async (req, res) => {
  try {
    const gameData = req.body;
    const gameId = await scheduleService.createGame(gameData);
    res.status(201).json({ id: gameId, message: 'Game created successfully' });
  } catch (error) {
    console.error('Error creating game:', error);
    res.status(500).json({ error: 'Failed to create game' });
  }
};

/**
 * Update game availability
 * PUT /api/schedule/games/:gameId/availability
 */
exports.updateGameAvailability = async (req, res) => {
  try {
    const { gameId } = req.params;
    const { status } = req.body;
    const userId = req.user.uid;

    if (!['in', 'out', 'maybe'].includes(status)) {
      return res.status(400).json({ error: 'Invalid status. Must be "in", "out", or "maybe"' });
    }

    await scheduleService.updateGameAvailability(gameId, userId, status);
    res.status(200).json({ message: 'Availability updated successfully' });
  } catch (error) {
    console.error('Error updating game availability:', error);
    res.status(500).json({ error: 'Failed to update availability' });
  }
};

// Test endpoints (no authentication required)
/**
 * Test endpoint for getting all games
 * GET /api/schedule/games/test
 */
exports.getAllGamesTest = async (req, res) => {
  try {
    console.log('Test endpoint: Getting all games');
    const games = await scheduleService.getAllGames();
    console.log(`Test endpoint: Found ${games.length} games`);
    res.status(200).json(games);
  } catch (error) {
    console.error('Error in test endpoint:', error);
    res.status(500).json({ error: 'Failed to get games' });
  }
};

/**
 * Test endpoint for getting game details
 * GET /api/schedule/games/:gameId/test
 */
exports.getGameDetailsTest = async (req, res) => {
  try {
    const { gameId } = req.params;
    console.log(`Test endpoint: Getting game details for ${gameId}`);
    const game = await scheduleService.getGameDetails(gameId);
    res.status(200).json(game);
  } catch (error) {
    console.error('Error in test endpoint:', error);
    if (error.message === 'Game not found') {
      res.status(404).json({ error: 'Game not found' });
    } else {
      res.status(500).json({ error: 'Failed to get game details' });
    }
  }
};

/**
 * Test endpoint for updating game availability
 * PUT /api/schedule/games/:gameId/availability/test
 */
exports.updateGameAvailabilityTest = async (req, res) => {
  try {
    const { gameId } = req.params;
    const { status, userId } = req.body;
    
    if (!['in', 'out', 'maybe'].includes(status)) {
      return res.status(400).json({ error: 'Invalid status. Must be "in", "out", or "maybe"' });
    }

    const testUserId = userId || 'test-user';
    console.log(`Test endpoint: Updating availability for game ${gameId}, user ${testUserId}, status ${status}`);
    
    await scheduleService.updateGameAvailability(gameId, testUserId, status);
    res.status(200).json({ message: 'Availability updated successfully' });
  } catch (error) {
    console.error('Error in test endpoint:', error);
    res.status(500).json({ error: 'Failed to update availability' });
  }
}; 