const express = require("express");
const mongoose = require("mongoose");
require("dotenv").config();

const app = express();

app.use(express.json({ extended: false }));

mongoose
  .connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
    useFindAndModify: false,
  })
  .then(() => {
    console.log("DATABASE CONNECTED");
  });

app.use("/api/auth", require("./routes/auth"));
app.use("/api/users", require("./routes/users"));
app.use("/api/docs", require("./routes/docs"));
app.use("/api/classes", require("./routes/classes"));
app.use("/api/queries", require("./routes/queries"));

app.listen(process.env.PORT || 8000, () => {
  console.log(`SERVER WORKING AT ${process.env.PORT}`);
});
