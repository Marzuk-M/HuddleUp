const admin = require('../firebase/admin');

/**
 * Get user profile information from Firebase
 */
exports.getUserProfile = async (req, res) => {
  try {
    const uid = req.user.uid;
    
    // Get user data from Firestore
    const userDoc = await admin.firestore()
      .collection('users')
      .doc(uid)
      .get();

    if (!userDoc.exists) {
      return res.status(404).json({ error: 'User not found' });
    }

    const userData = userDoc.data();
    
    // Get user creation date from Firebase Auth
    const userRecord = await admin.auth().getUser(uid);
    const memberSince = new Date(userRecord.metadata.creationTime).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short'
    });

    // Get notification settings from user_settings collection
    const notificationDoc = await admin.firestore()
      .collection('user_settings')
      .doc(uid)
      .get();

    const notificationEnabled = notificationDoc.exists ? 
      notificationDoc.data().notificationEnabled ?? true : true;

    const profile = {
      name: `${userData.first_name} ${userData.last_name}`,
      email: userData.email,
      username: userData.username,
      memberSince: memberSince,
      notificationEnabled: notificationEnabled
    };

    res.status(200).json(profile);
  } catch (error) {
    console.error('Error getting user profile:', error);
    res.status(500).json({ error: 'Failed to get user profile' });
  }
};

/**
 * Update user's display name
 */
exports.updateUserName = async (req, res) => {
  try {
    const uid = req.user.uid;
    const { name } = req.body;

    if (!name || name.trim().length === 0) {
      return res.status(400).json({ error: 'Name is required' });
    }

    // Split name into first and last name
    const nameParts = name.trim().split(' ');
    const firstName = nameParts[0] || '';
    const lastName = nameParts.slice(1).join(' ') || '';

    // Update Firebase Auth display name
    await admin.auth().updateUser(uid, {
      displayName: name
    });

    // Update Firestore user document
    await admin.firestore()
      .collection('users')
      .doc(uid)
      .update({
        first_name: firstName,
        first_name_lower: firstName.toLowerCase(),
        last_name: lastName,
        last_name_lower: lastName.toLowerCase()
      });

    res.status(200).json({ message: 'Name updated successfully' });
  } catch (error) {
    console.error('Error updating user name:', error);
    res.status(500).json({ error: 'Failed to update name' });
  }
};

/**
 * Update user's notification preferences
 */
exports.updateNotificationSettings = async (req, res) => {
  try {
    const uid = req.user.uid;
    const { notificationEnabled } = req.body;

    if (typeof notificationEnabled !== 'boolean') {
      return res.status(400).json({ error: 'notificationEnabled must be a boolean' });
    }

    // Update notification settings in Firestore
    await admin.firestore()
      .collection('user_settings')
      .doc(uid)
      .set({
        notificationEnabled: notificationEnabled,
        updatedAt: admin.firestore.FieldValue.serverTimestamp()
      }, { merge: true });

    res.status(200).json({ message: 'Notification settings updated successfully' });
  } catch (error) {
    console.error('Error updating notification settings:', error);
    res.status(500).json({ error: 'Failed to update notification settings' });
  }
}; 