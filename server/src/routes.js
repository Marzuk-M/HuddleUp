const express = require('express');
const router = express.Router();

// Controllers
const authController = require('./controllers/auth.controller');
const dashboardController = require('./controllers/dashboard.controller');
const scheduleController = require('./controllers/schedule.controller');

const notificationsController = require('./controllers/notifications.controller');

const authenticateToken = require('./middlewares/auth.middleware');

// Auth Routes
router.post('/auth/signup', authController.signup);
router.get('/auth/username/:username', authController.isUsernameTaken);

// Dashboard Routes
router.get('/dashboard/test', authenticateToken, dashboardController.test);

// Schedule Test Routes (no authentication required) - These must come BEFORE the parameterized routes
router.get('/schedule/games/test', scheduleController.getAllGamesTest);
router.get('/schedule/games/:gameId/test', scheduleController.getGameDetailsTest);
router.put('/schedule/games/:gameId/availability/test', scheduleController.updateGameAvailabilityTest);

// Schedule Routes (with authentication)
router.get('/schedule/my-games', authenticateToken, scheduleController.getUserGames);
router.get('/schedule/games', authenticateToken, scheduleController.getAllGames);
router.get('/schedule/games/:gameId', authenticateToken, scheduleController.getGameDetails);
router.post('/schedule/games', authenticateToken, scheduleController.createGame);
router.put('/schedule/games/:gameId/availability', authenticateToken, scheduleController.updateGameAvailability);

// Notifications Routes
router.get('/notifications', authenticateToken, notificationsController.getNotifications);
router.put('/notifications/:notificationId/read', authenticateToken, notificationsController.markAsRead);
router.post('/notifications', authenticateToken, notificationsController.createNotification);

module.exports = router;