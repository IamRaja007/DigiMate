const jwt = require("jsonwebtoken");
require("dotenv").config();

module.exports = function (req, res, next) {
  const token = req.header("auth-token");
  if (!token) {
    return res.status(400).send("No token, authorization denied");
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SCERET, {
      expiresIn: 1000000000000,
    });

    req.user = decoded.user;
    next();
  } catch (error) {
    res.status(401).send("Inavlid Token");
  }
};
