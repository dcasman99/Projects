const { response } = require('express');
const express = require('express');
const { db, findOneAndUpdate } = require('../models/student-model');
const router = express.Router();

const Student = require('../models/student-model');

//get all
router.get('/', async (req, res) => { // promise 

    const students = await Student.find();
    res.status(200).json(students);

    // res.send('You are on the home page for students');
});

// add student
router.post('/', async (req, res) => {
    const student = new Student({
        firstname: req.body.firstname,
        lastname: req.body.lastname,
        major: req.body.major,
        birthDate: req.body.birthDate
    });

    try {
        const result = await student.save();
        res.json(result);
    } catch (err) {
        res.json({ message: err })
    }
});

//update route
router.post('/:id', async (req, res) => {
    res.send(`You posted request to get student with ID: ${req.params.id}`);
    const student = {
        firstname: req.body.firstname, 
        lastname: req.body.lastname,
        major: req.body.major,
        birthday: req.body.birthday
    };
    try {
        const result = await Student.findByIdAndUpdate(req.params.id, student, { new: true });
        res.json(result);
    } catch (error) {
        res.json({message: error});
    }
});

// delete route
router.delete('/:id', async (req, res) => {
    const id = req.params.id;
    const result = Student.findByIdAndDelete(id);
    try {
        res.json(result);
    } catch (error) {
        res.json({message: error});
    }
});



module.exports = router;