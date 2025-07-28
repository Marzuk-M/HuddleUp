const express = require('express');
const router = express.Router();

// Controllers
const authController = require('./controllers/auth.controller');
const dashboardController = require('./controllers/dashboard.controller');
const teamController = require('./controllers/team.controller');
const authenticateToken = require('./middlewares/auth.middleware');

// Auth Routes
router.post('/auth/signup', authController.signup);
router.get('/auth/username/:username', authController.isUsernameTaken);

// Dashboard Routes
router.get('/dashboard/test', authenticateToken, dashboardController.test);

// Team Routes (with authentication)
router.get('/teams/search', authenticateToken, teamController.searchTeams);
router.get('/teams/:teamId', authenticateToken, teamController.getTeamDetails);
router.post('/teams', authenticateToken, teamController.createTeam);
router.get('/teams/my-teams', authenticateToken, teamController.getUserTeams);

// Team Membership Routes
router.post('/teams/:teamId/join', authenticateToken, teamController.sendJoinRequest);
router.delete('/teams/:teamId/join', authenticateToken, teamController.unsendJoinRequest);
router.delete('/teams/:teamId/leave', authenticateToken, teamController.leaveTeam);

// Temporary test endpoints (no authentication required)
router.get('/teams/search/test', teamController.searchTeamsTest);
router.get('/teams/my-teams/test', teamController.getUserTeamsTest);
router.post('/teams/:teamId/join/test', teamController.sendJoinRequestTest);
router.delete('/teams/:teamId/join/test', teamController.unsendJoinRequestTest);
router.delete('/teams/:teamId/leave/test', teamController.leaveTeamTest);

module.exports = router;