const express = require("express");
const bcrypt = require("bcrypt");
const User = require("../models/User");
const auth = require("../middleswares/auth");
const formidable = require("formidable");
const fs = require("fs");
const router = express.Router();

// @route POST api/users
// @desc register user
// access Private(Admin)
router.post("/", (req, res) => {
  let form = new formidable.IncomingForm();
  form.keepExtensions = true;

  form.parse(req, async (err, fields, files) => {
    if (err) {
      console.log(err);
    }

    let user = new User(fields);
    const { password } = user;

    const salt = await bcrypt.genSalt(10);
    user.password = await bcrypt.hash(password, salt);

    if (files.profilepic) {
      user.profilepic.data = fs.readFileSync(files.profilepic.path);
      user.profilepic.contentType = files.profilepic.type;
    }

    user.save((err, result) => {
      if (err) {
        console.log(err);
      }

      return res.status(200).json(result);
    });
  });
});

// @route PUT api/users/edit-user
// @desc to edit user details
// @access Private
router.put("/edit-user", auth, async (req, res) => {
  const user = await User.findById(req.user.id).select("-password");
  let form = new formidable.IncomingForm();
  form.keepExtensions = true;
  form.parse(req, async (err, fields, files) => {
    if (err) {
      console.log(err);
    }

    if (files.profilepic) {
      user.profilepic.data = fs.readFileSync(files.profilepic.path);
      user.profilepic.contentType = files.profilepic.type;
    }

    user.save((err, result) => {
      if (err) {
        console.log(err);
      }

      return res.status(200).json(result);
    });
  });
});

// @router api/users/my-profile
// @desc get my profile
// @access Private
router.get("/my-profile", auth, (req, res) => {
  User.findById(req.user.id)
    .select("-password -queries")
    .exec(async (err, user) => {
      if (err) {
        console.log(err);
      }
      if (user.designation == "Student") {
        const { userId } = user.proctoredBy;
        const proctor = await User.findOne({ userId }).select(
          "-password -proctoring"
        );
        user.proctoredBy = undefined;
        return res.status(200).json({ user, proctor });
      }
      if (user.isproctor == true) {
        return res.status(200).json({ user });
      }
    });
});

// @route api/users/change-password/:emailID
// @desc Change User password
// @access public
router.put("/change-password/:emailID", (req, res) => {
  const { password } = req.body;
  User.findOne({ email: req.params.emailID }).exec(async (err, user) => {
    const salt = await bcrypt.genSalt(10);
    user.password = await bcrypt.hash(password, salt);

    user.save((err, result) => {
      if (err) {
        console.log(err);
      }

      return res.status(200).json(result);
    });
  });
});

// @route PUT api/users/:proctor_id/proctoring/:student_id
// @desc assign proctor to a student(add a student to proctoring[])
// @access Private(Admin Only)
/* userId (AD001, ST001, FA001 ...) is not same as _id */
router.get("/:proctor_userId/proctoring/:student_userId", auth, (req, res) => {
  User.findById(req.user.id).exec(async (err, result) => {
    if (err) {
      console.log(err);
    }

    if (result.designation !== "Admin") {
      return res.status(400).json("Authorization denied");
    }

    const student = await User.findOne({
      userId: req.params.student_userId,
    }).select("-password");

    const proctor = await User.findOne({
      userId: req.params.proctor_userId,
    }).select("-password");

    proctor.proctoring.unshift({
      studentId: student._id,
      userId: student.userId,
      name: student.name,
      profilepic: student.profilepic,
      department: student.department,
      semester: student.semester,
    });

    student.proctoredBy.userId = req.params.proctor_userId;
    student.proctoredBy.proctorId = proctor._id;
    student.proctoredBy.name = proctor.name;

    student.save((se, s) => {
      if (se) {
        console.log(se);
      }
    });

    proctor.save((pe, p) => {
      if (pe) {
        console.log(pe);
      }
      return res.status(200).json(p);
    });
  });
});

// @router api/users/:proctor_userId/get-students
// @desc get proctered students by a particular proctor
// @access Private
router.get("/get-students", auth, (req, res) => {
  User.findById(req.user.id)
    .select("-password")
    .exec((err, user) => {
      if (err) {
        console.log(err);
      }

      return res.status(200).json({ proctoring: user.proctoring });
    });
});

// @route api/users/get-proctor-profile
// @desc get proctor profile
// @access Private(Student)
router.get("/get-myproctor-profile", auth, (req, res) => {
  User.findById(req.user.id)
    .select("-password")
    .exec(async (err, user) => {
      if (err) {
        console.log(err);
      }
      const { userId } = user.proctoredBy;
      const proctor = await User.findOne({ userId }).select("-password");

      return res.status(200).json(proctor);
    });
});

// @route GET api/users/view-profilepic/:user_id
// @desc display user profilepic
// @access Public
router.get("/view-profilepic/:user_id", async (req, res) => {
  const user = await User.findById(req.params.user_id).select("-password");
  const { profilepic } = user;
  if (profilepic) {
    res.set("Content-Type", profilepic.contentType);
    return res.send(profilepic.data);
  }
});

module.exports = router;
