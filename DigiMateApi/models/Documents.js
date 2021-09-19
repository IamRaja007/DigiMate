const mongoose = require("mongoose");

const documentSchema = mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "users",
  },
  text: {
    type: String,
  },
  department: {
    type: String,
  },
  semester: {
    type: String,
  },
  institution: {
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
  date: {
    type: String,
  },
  time: {
    type: String,
  },
  reply: [
    {
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
    },
  ],
});

module.exports = Docs = mongoose.model("doc", documentSchema);
