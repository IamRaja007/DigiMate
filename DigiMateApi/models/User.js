const mongoose = require("mongoose");

const userSchema = mongoose.Schema({
  name: {
    type: String,
  },
  email: {
    type: String,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  profilepic: {
    data: Buffer,
    contentType: String,
  },
  userId: {
    type: String,
    unique: true,
  },
  designation: {
    type: String,
  },
  institution: {
    type: String,
  },
  department: {
    type: String,
  },
  semester: {
    type: String,
  },
  /* if the given faculty is proctor or not */
  isproctor: {
    type: Boolean,
    default: false,
  },
  /* for student */
  proctoredBy: {
    proctorId: {
      type: mongoose.Schema.Types.ObjectId,
      req: "users",
    },
    userId: {
      type: String,
    },
    name: {
      type: String,
    },
  },
  /* for faculty (proctor) array of students whom he/she 
  is proctoring */
  proctoring: [
    {
      studentId: {
        type: String,
      },
      userId: {
        type: String,
      },
      name: {
        type: String,
      },
      profilepic: {
        data: Buffer,
        contentType: String,
      },
      department: {
        type: String,
      },
      semester: {
        type: String,
      },
    },
  ],
});

module.exports = User = mongoose.model("user", userSchema);
