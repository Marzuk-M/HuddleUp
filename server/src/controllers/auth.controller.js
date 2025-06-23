const authService = require('../services/auth.service');

exports.signup = async (req, res) => {
  const {
    email,
    password,
    first_name,
    last_name,
    username,
    date_of_birth
  } = req.body;

  try {
    const uid = await authService.registerUser({
      email,
      password,
      first_name,
      last_name,
      username,
      date_of_birth
    });

    res.status(201).json({ message: 'User created successfully', uid });
  } catch (error) {
    console.error(error);
    if (error.code && error.code.startsWith('auth/')) {
      res.status(400).json({ error: `Firebase error: ${error.message}` });
    } else {
      res.status(500).json({ error: `Signup failed: ${error.message}` });
    }
  }
};

exports.isUsernameTaken = async (req, res) => {
  const { username } = req.body;

  try {
    const result = await authService.isUsernameTaken(username);
    res.status(200).json({ taken: result });
  } catch (error) {
    res.status(503).json({ taken: true });
  }
};