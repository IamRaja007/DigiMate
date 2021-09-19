const express = require("express");
const router = express.Router();
const formidable = require("formidable");
const fs = require("fs");
const auth = require("../middleswares/auth");
const Documents = require("../models/Documents");
const Docs = require("../models/Documents");
const User = require("../models/User");

// @route POST api/docs
// @desc share docs
// @access Private
router.post("/", auth, async (req, res) => {
  let form = new formidable.IncomingForm();
  form.keepExtentions = true;
  const user = await User.findById(req.user.id).select("-password");

  form.parse(req, (err, fields, files) => {
    if (err) {
      console.log(err);
    }

    var date = Date();
    date = String(date).substring(0, 15);

    let doc = new Docs(fields);
    doc.user = req.user.id;
    doc.name = user.name;
    doc.institution = user.institution;
    doc.date = date;

    if (files.photo) {
      doc.photo.data = fs.readFileSync(files.photo.path);
      doc.photo.contentType = files.photo.type;
    }

    if (files.pdf) {
      doc.pdfdocument.data = fs.readFileSync(files.pdf.path);
      doc.pdfdocument.contentType = files.pdf.type;
    }

    doc.save((err, result) => {
      if (err) {
        console.log(err);
      }

      return res.status(200).json(result);
    });
  });
});

// @route GET api/docs/get-all-docs
// @desc get all docs
// @access Private
router.get("/get-all-docs", auth, (req, res) => {
  User.findById(req.user.id)
    .select("-password")
    .exec((error, user) => {
      if (error) {
        console.log(error);
      }
      if (user.designation == "Faculty") {
        Docs.find({ user }).exec((err, docs) => {
          if (err) {
            console.log(err);
          }
          return res.status(200).json({ docs });
        });
      }

      if (user.designation == "Student") {
        const { institution } = user;
        Docs.find({
          institution,
        }).exec((e, docs) => {
          if (e) {
            console.log(e);
          }
          return res.status(200).json({ docs });
        });
      }
    });
});

// @route PUT api/docs/:doc_id/write-reply
// @desc write reply to a particular document shared
// @access Private
router.put("/:doc_id/write-reply", auth, async (req, res) => {
  const user = await User.findById(req.user.id).select("-password");
  const docs = await Docs.findById(req.params.doc_id);
  const { text } = req.body;
  docs.reply.unshift({
    user: user._id,
    name: user.name,
    text,
  });
  docs.save((err, d) => {
    if (err) {
      console.log(err);
    }

    return res.json(docs);
  });
});

// @route GET api/docs/view-photo/:doc_id
// @desc display photo attached to a document
// @access Public
router.get("/view-photo/:doc_id", async (req, res) => {
  const doc = await Documents.findById(req.params.doc_id);
  const { photo } = doc;

  if (photo) {
    res.set("Content-Type", photo.contentType);
    return res.send(photo.data);
  }
});

// @route GET api/docs/view-pdf/:doc_id
// @desc display  attached to a document
// @access Public
router.get("/view-pdf/:doc_id", async (req, res) => {
  const doc = await Documents.findById(req.params.doc_id);
  const { pdfdocument } = doc;

  if (pdfdocument) {
    res.set("Content-Type", pdfdocument.contentType);
    return res.send(pdfdocument.data);
  }
});

module.exports = router;
