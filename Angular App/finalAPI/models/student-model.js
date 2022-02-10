const mongoose = require('mongoose');

const StudentSchema = mongoose.Schema({
    firstname: {
        type: String,
        required: true
    },
    lastname: {
        type: String,
        required: true
    },
    major: {
        type: String,
        required: true
    },
    birthDate: {
        type: Date
    },
    createdOn: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('Students', StudentSchema);