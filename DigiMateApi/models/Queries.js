const mongoose = require("mongoose");

const querySchema = mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "users",
  },
  name: {
    type: String,
  },
  text: {
    type: String,
  },
  photo: {
    data: Buffer,
    contentType: String,
  },
  pdfdocument: {
    data: Buffer,
    contentType: String,
  },
  to: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "users",
  },
  reply: [
    {
      text: {
        type: String,
      },
      user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "users",
      },
      name: {
        type: String,
      },
    },
  ],
  /* pass the then(when the query is made) date   */
  date: {
    type: String,
  },
  /* pass the then(when the query is made) time */
  time: {
    type: String,
  },
});

module.exports = Queries = mongoose.model("query", querySchema);
