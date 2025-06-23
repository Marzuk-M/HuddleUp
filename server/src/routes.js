const express = require('express');
const router = express.Router();

// Controllers
const authController = require('./controllers/auth.controller');
const dashboardController = require('./controllers/dashboard.controller');
const authenticateToken = require('./middlewares/auth.middleware');

// Auth Routes
router.post('/auth/signup', authController.signup);
router.get('/auth/username', authController.isUsernameTaken);

// Dashboard Routes
router.get('/dashboard/test', authenticateToken, dashboardController.test);

module.exports = router;