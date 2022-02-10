const express = require('express');
const app = express();

// We will use mongoose to connect to our MongoDB
const mongoose = require('mongoose');
const nodemailer = require('nodemailer');

const cors = require('cors');

const PORT = 3030;

const studentRoutes = require('./routes/student-route');
const userRoutes = require('./routes/user-route');

// middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// API routes
app.use('/student', studentRoutes);
app.use('/user', userRoutes);

// default route
app.get('/', (req, res) => {
    res.send('You are on the default page!');
});

// connect to database
mongoose.connect(
    'mongodb+srv://dbUser:12345@cluster0.fuxfm.mongodb.net/database1?retryWrites=true&w=majority',
    // 'mongodb+srv://db_user1:Password123@cluster0.yphqp.mongodb.net/database1',
    { useNewUrlParser: true, useUnifiedTopology: true }
)
    .then(() => console.log('Connected to the database successfully!'))
    .catch(err => console.error('Error connecting to DB:', err.message));

// listen to port on server
app.listen(PORT, () => console.log(`server is running at http://localhost:${PORT}`));
