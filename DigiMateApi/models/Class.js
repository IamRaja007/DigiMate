const mongoose = require("mongoose");

const classSchema = mongoose.Schema({
  classLink: {
    type: String,
  },
  topic: {
    type: String,
  },
  facultyId: {
    type: String,
  },
  facultyName: {
    type: String,
  },
  classID: {
    type: String,
  },
  // attendees: [
  //   {
  //     user: {
  //       type: mongoose.Schema.Types.ObjectId,
  //       ref: "users",
  //     },
  //     userId: {
  //       type: String,
  //     },
  //     name: {
  //       type: String,
  //     },
  //   },
  // ],
  time: {
    type: String,
  },
  date: {
    type: String,
  },
});

module.exports = Class = mongoose.model("class", classSchema);
