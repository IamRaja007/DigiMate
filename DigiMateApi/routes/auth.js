const express = require("express");
const User = require("../models/User");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const router = express.Router();

// @route POST api/auth
// @desc authentication and get token
// @access Public
router.post("/", async (req, res) => {
  const { userId, password } = req.body;

  try {
    let user = await User.findOne({ userId });

    if (!user) {
      return res.status(400).send("inavlid credentials");
    }

    const isMatch = await bcrypt.compareSync(password, user.password);
    if (!isMatch) {
      return res.status(400).send("invalid password");
    }

    const payload = {
      user: {
        id: user.id,
      },
    };

    user.password = undefined;

    const token = jwt.sign(payload, process.env.JWT_SCERET, {
      expiresIn: 1000000000000,
    });
    res.cookie("token", token, { expiresIn: 1000000000000 });
    return res.status(200).json({ token, user });
  } catch (err) {
    console.log(err.message);
    res.status(500).send("Server Error");
  }
});

module.exports = router;
