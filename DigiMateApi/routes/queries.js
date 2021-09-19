const express = require("express");
const router = express.Router();
const User = require("../models/User");
const auth = require("../middleswares/auth");
const formidable = require("formidable");
const fs = require("fs");
const Queries = require("../models/Queries");

// @route POST api/queries
// @desc create query
// @access Private
router.post("/", auth, async (req, res) => {
  let form = new formidable.IncomingForm();
  const user = await User.findById(req.user.id);
  const { proctoredBy } = user;

  form.parse(req, (err, fields, files) => {
    if (err) {
      console.log(err);
    }

    let date = Date();
    date = String(date).substring(0, 15);

    let query = new Queries(fields);
    query.user = req.user.id;
    query.to = proctoredBy.proctorId;
    query.name = user.name;
    query.date = date;

    if (files.photo) {
      query.photo.data = fs.readFileSync(files.photo.path);
      query.photo.contentType = files.photo.type;
    }

    if (files.pdf) {
      query.pdfdocument.data = fs.readFileSync(files.pdf.path);
      query.pdfdocument.contentType = files.pdf.type;
    }

    query.save((e, q) => {
      if (e) {
        console.log(e);
      }
      return res.status(200).json(q);
    });
  });
});

// @route PUT api/queries/:queryId/write-reply
// @desc write reply to a particular query
// @access private
router.put("/:queryID/write-reply", auth, async (req, res) => {
  const { text } = req.body;
  const user = await User.findById(req.user.id).select("-password");

  Queries.findById(req.params.queryID).exec((error, query) => {
    if (error) {
      console.log(error);
    }
    query.reply.unshift({ text, user, name: user.name });
    query.save((e, q) => {
      if (e) {
        console.log(e);
      }

      return res.status(200).json(q);
    });
  });
});

// @route GET api/queries/get-all-queries
// @desc get all the queries posted to the proctor
// @access Private
router.get("/get-all-queries", auth, async (req, res) => {
  const user = await User.findById(req.user.id).select("-password");
  const { _id } = user;

  if (user.isproctor == true) {
    Queries.find({ to: _id }).exec((err, q) => {
      if (err) {
        console.log(err);
      }
      return res.status(200).json({ queries: q });
    });
  } else {
    Queries.find({ user: _id }).exec((err, q) => {
      if (err) {
        console.log(err);
      }
      return res.status(200).json({ queries: q });
    });
  }
});

// @route GET api/queries/:queryID/get-all-reply
// @desc get replies to a particular query
// @access Private
router.get("/:queryID/get-all-replies", auth, async (req, res) => {
  Queries.findById(req.params.queryID).exec((e, q) => {
    if (e) {
      console.log(e);
    }
    return res.status(200).json({ reply: q.reply });
  });
});

// @route GET api/queries/view-photo/:queryID
// @desc view photo or pdf attached to a query
// @access Public
router.get("/view-query-photo/:query_id", async (req, res) => {
  const query = await Queries.findById(req.params.query_id);
  const { photo } = query;

  if (photo) {
    res.set("Content-Type", photo.contentType);
    return res.send(photo.data);
  }
});

// @route GET api/queries/view-pdf/:queryID
// @desc view photo or pdf attached to a query
// @access Public
router.get("/view-query-pdf/:query_id", async (req, res) => {
  const query = await Queries.findById(req.params.query_id);
  const { pdfdocument } = query;

  if (pdfdocument) {
    res.set("Content-Type", pdfdocument.contentType);
    return res.send(pdfdocument.data);
  }
});

module.exports = router;
