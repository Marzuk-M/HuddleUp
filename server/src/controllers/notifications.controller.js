const admin = require('../firebase/admin');

const notificationsController = {
    // Get notifications for the authenticated user
    async getNotifications(req, res) {
        try {
            const userId = req.user.uid;
            
            // For now, we'll return mock data based on the user
            // In a real implementation, you'd fetch from a database
            const mockNotifications = [
                {
                    notificationBlockTitle: "New",
                    notifications: [
                        {
                            teamId: "team123",
                            teamName: "CS Study Group",
                            message: "Jane: Meeting at 7pm today?",
                            timestamp: Date.now() - 1 * 60 * 60 * 1000, // 1 hour ago
                            type: "CHAT"
                        },
                        {
                            message: "Your profile was viewed 5 times today!",
                            timestamp: Date.now() - 2 * 60 * 60 * 1000, // 2 hours ago
                            type: "SYSTEM"
                        }
                    ]
                },
                {
                    notificationBlockTitle: "Yesterday",
                    notifications: [
                        {
                            teamId: "team456",
                            teamName: "Dance Crew",
                            message: "Mike: Don't forget practice tomorrow!",
                            timestamp: Date.now() - 24 * 60 * 60 * 1000, // 1 day ago
                            type: "CHAT"
                        },
                        {
                            message: "Your team 'Dance Crew' has been approved!",
                            timestamp: Date.now() - 26 * 60 * 60 * 1000, // 26 hours ago
                            type: "SYSTEM"
                        }
                    ]
                }
            ];

            res.json({
                success: true,
                data: mockNotifications
            });
        } catch (error) {
            console.error('Error fetching notifications:', error);
            res.status(500).json({
                success: false,
                message: 'Failed to fetch notifications'
            });
        }
    },

    // Mark a notification as read
    async markAsRead(req, res) {
        try {
            const { notificationId } = req.params;
            const userId = req.user.uid;
            
            // In a real implementation, you'd update the database
            // For now, just return success
            res.json({
                success: true,
                message: 'Notification marked as read'
            });
        } catch (error) {
            console.error('Error marking notification as read:', error);
            res.status(500).json({
                success: false,
                message: 'Failed to mark notification as read'
            });
        }
    },

    // Create a new notification (for testing or admin use)
    async createNotification(req, res) {
        try {
            const { type, message, teamId, teamName } = req.body;
            const userId = req.user.uid;
            
            // In a real implementation, you'd save to database
            // For now, just return success
            res.json({
                success: true,
                message: 'Notification created successfully'
            });
        } catch (error) {
            console.error('Error creating notification:', error);
            res.status(500).json({
                success: false,
                message: 'Failed to create notification'
            });
        }
    }
};

module.exports = notificationsController; 