const express = require('express');
const { response } = require('express');
const router = express.Router();
const { db, findOneAndUpdate } = require('../models/user-model');
const nodemailer = require('nodemailer');

const User = require('../models/user-model');

router.get('/', async (req, res) => { // promise 

    const users = await User.find();
    res.status(200).json(users);
});

router.get('/:userId', (req, res) => {
    const id = req.params.userId;

    res.send('You want to get a record for student with ID: ' + id);
});

router.post('/', async (req, res) => {
    const user = new User({
        firstname: req.body.firstname,
        lastname: req.body.lastname,
        email: req.body.email,
        phone: req.body.phone
    });

    try {
        const result = await user.save();
        res.json(result);
    } catch (err) {
        res.json({ message: err })
    }
});

module.exports = router;