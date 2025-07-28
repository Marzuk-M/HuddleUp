const express = require('express');
const router = express.Router();

// Controllers
const authController = require('./controllers/auth.controller');
const dashboardController = require('./controllers/dashboard.controller');
const notificationsController = require('./controllers/notifications.controller');
const authenticateToken = require('./middlewares/auth.middleware');

// Auth Routes
router.post('/auth/signup', authController.signup);
router.get('/auth/username/:username', authController.isUsernameTaken);

// Dashboard Routes
router.get('/dashboard/test', authenticateToken, dashboardController.test);

// Notifications Routes
router.get('/notifications', authenticateToken, notificationsController.getNotifications);
router.put('/notifications/:notificationId/read', authenticateToken, notificationsController.markAsRead);
router.post('/notifications', authenticateToken, notificationsController.createNotification);

module.exports = router;