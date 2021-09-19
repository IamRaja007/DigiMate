const express = require("express");
const Class = require("../models/Class");
const User = require("../models/User");
const auth = require("../middleswares/auth");
const randomstring = require("randomstring");
const router = express.Router();
const { GoogleSpreadsheet } = require("google-spreadsheet");
const GOOGLE_SHEET_ID = process.env.GOOGLE_SHEET_ID;
const doc = new GoogleSpreadsheet(GOOGLE_SHEET_ID);
require("dotenv").config();

// @route POST api/classes
// @desc create a class
// access Private
router.post("/", auth, (req, res) => {
  var { classLink, topic, time } = req.body;
  var date = Date();

  date = String(date).substring(0, 15);

  Class.find({}).exec((e, c) => {
    if (e) {
      console.log(e);
    }
    c.map((i) => {
      if (date !== i.date) {
        i.delete();
      }
    });
  });

  User.findById(req.user.id)
    .select("-password")
    .exec((error, user) => {
      if (error) {
        console.log(error);
      }
      if (user.designation === "Faculty") {
        const classID = randomstring.generate({
          length: 6,
          charset: "alphabetic",
          capitalization: "uppercase",
        });
        let generatedClass = new Class({
          classLink,
          classID,
          topic,
          date,
          time,
          facultyId: user.userId,
          facultyName: user.name,
        });

        generatedClass.save((err, c) => {
          if (err) {
            console.log(err);
          }

          return res.status(200).json(c);
        });
      }
    });
});

const searchStudent = async (class_id, userId) => {
  await doc.useServiceAccountAuth({
    client_email: process.env.CLIENT_EMAIL,
    private_key: process.env.PRIVATE_KEY,
  });

  // load the document info
  await doc.loadInfo();

  // Index of the sheet
  let sheet = doc.sheetsByIndex[0];

  // Get all the rows
  let rows = await sheet.getRows();
  for (let i = 0; i < rows.length; i++) {
    const row = rows[i];
    if (row.CLASS_ID === class_id && row.USER_ID === userId) {
      return true;
    }
  }
};

// @route put api/classes/attend
// @desc grant attendence for students
// @access private
router.get("/attend/:class_id", auth, async (req, res) => {
  const getClass = await Class.findOne({ _id: req.params.class_id });
  const user = await User.findById(req.user.id).select("-password");
  // let alreadyPresent = false;

  let rows = [
    {
      DATE: getClass.date,
      CLASS_ID: getClass.classID,
      FACULTY_ID: getClass.facultyId,
      USER_ID: user.userId,
      NAME: user.name,
    },
  ];

  // const addRow = async (rows) => {
  // console.log("Working");
  //use service account creds
  await doc.useServiceAccountAuth({
    client_email: process.env.CLIENT_EMAIL,
    private_key: process.env.PRIVATE_KEY,
  });

  // load the document info
  await doc.loadInfo();

  // Index of the sheet
  let sheet = doc.sheetsByIndex[0];

  searchStudent(getClass.classID, user.userId).then(async (found) => {
    if (found != true) {
      for (let i = 0; i < rows.length; i++) {
        const row = rows[i];
        await sheet.addRow(row);
      }
    }
    return res.status(200).json("SUCCESS");
  });

  // for (let i = 0; i < rows.length; i++) {
  //   const row = rows[i];
  //   await sheet.addRow(row).then(() => {
  //     return res.status(200).json("SUCCESS");
  //   });
  // }
  // };

  // addRow(rows);

  // getClass.attendees.map((s) => {
  //   if (s.userId === user.userId) {
  //     alreadyPresent = true;
  //   }
  // });
  // if (alreadyPresent == true) {
  //   res.status(200).json("Already Present");
  // } else {
  //   getClass.attendees.unshift({ userId: user.userId, name: user.name });

  //   getClass.save((err, result) => {
  //     if (err) {
  //       console.log(err);
  //     }
  //     return res.status(200).json(result);
  //   });
  // }
});

// @route GET api/classs/get-facultys-class
// @desc get the class generated by the faculty
// @access Private
router.get("/get-facultys-class", auth, async (req, res) => {
  var date = Date();
  date = String(date).substring(0, 15);
  const user = await User.findById(req.user.id).select("-password");
  const { userId } = user;
  const fClass = await Class.find({ date, facultyId: userId });
  return res.status(200).json({ classes: fClass });
});

// @router GET  api/classes/:class_id/attendence-list
// @desc get a class attendence list
// @access Private
router.get("/:class_id/attendence-list", auth, async (req, res) => {
  const fClass = await Class.findById(req.params.class_id);
  const { classID } = fClass;

  let attendees = [];
  await doc.useServiceAccountAuth({
    client_email: process.env.CLIENT_EMAIL,
    private_key: process.env.PRIVATE_KEY,
  });

  // load the document info
  await doc.loadInfo();

  // Index of the sheet
  let sheet = doc.sheetsByIndex[0];

  // Get all the rows
  let rows = await sheet.getRows();

  for (let i = 0; i < rows.length; i++) {
    const row = rows[i];
    if (row.CLASS_ID == classID) {
      attendees.push({ userId: row.USER_ID, name: row.NAME });
    }
  }
  return res.status(200).json({ attendees });

  // getRow(date, facultyId).then((attendess) => {
  //   return res.json({ attendess });
  // });

  // Class.findById(req.params.class_id).exec((err, c) => {
  //   if (err) {
  //     console.log(err);
  //   }
  //   return res.json({ attendees: c.attendees });
  // });
});

// @route GET api/classes/todays-classes
// @desc get all classes scheduled for today
// @access Private
router.get("/todays-classes", async (req, res) => {
  var date = Date();
  date = String(date).substring(0, 15);
  Class.find({ date }).exec((e, classes) => {
    if (e) {
      console.log(e);
    }
    return res.status(200).json({ classes });
  });
});

/* use this to remove a particular days class on 23:59 everyday */
// cron.schedule("* * * * *", async () => {
//   var time = Date();
//   var timeInString = String(time);
//   var date = String(time).substring(0, 15);

//   if (timeInString.substring(16, 21) === "23:59") {
//     const result = await Class.remove({ date });
//     console.log(result);
//   }
// });

module.exports = router;
