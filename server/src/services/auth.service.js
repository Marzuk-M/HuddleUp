const admin = require('../firebase/admin');

/**
 * Check if a username already exists in the database
 * 
 * @param {string} username 
 * @returns 
 */
async function isUsernameTaken(username) {
  if (!username || !username.trim()) {
    throw new Error('username is required');
  }

  const usernameQuery = await admin.firestore()
    .collection('users')
    .where('username', '==', username)
    .limit(1)
    .get();

  return !usernameQuery.empty;
}

/**
 * Register New User.
 * 
 * @param {*} param0 
 * @returns 
 */
async function registerUser({ email, password, first_name, last_name, username, date_of_birth }) {
  if (!email || !password || password.length < 6) {
    throw new Error('Password must be at least 6 characters long');
  }

  // Check for unique username
  if (await isUsernameTaken(username)) throw new Error('Username already taken');

  // Firebase Auth enforces unique emails
  const userRecord = await admin.auth().createUser({
    email,
    password,
    displayName: `${first_name} ${last_name}`
  });

  const userData = {
    uid: userRecord.uid,
    email,
    first_name,
    first_name_lower: first_name.toLowerCase(),
    last_name,
    last_name_lower: last_name.toLowerCase(),
    username,
    date_of_birth,
    teams: [],
  };

  const welcomeNotif = {
    message: 'Welcome to HuddleUp! We are so glad you are here!!',
    timestamp: Date.now(),
    system: true
  }

  await admin.firestore().collection('users').doc(userRecord.uid).set(userData);
  await admin.firestore().collection('notifications').doc(userRecord.uid).set(welcomeNotif);

  return userRecord.uid;
}

module.exports = {
  registerUser,
  isUsernameTaken
};
