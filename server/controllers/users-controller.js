var model = require('../models/user'),
    User = require('mongoose').model('User'),
    userHelpers = require('../helpers/user-helpers');

function create(req, res) {
    var user = req.body;
    var salt = userHelpers.generateSalt();
    user.salt = salt;
    var hash = userHelpers.hash(user.password, salt);
    user.password = hash;

    User.create(user, function (err, user) {
        if (err) {
            console.log('Could not register user: ' + err);
            return;
        } else {
            res.send(user);
        }
    });
}

function all(req, res) {
    User.find({}).exec(function (err, collection) {
        if (err) {
            console.log('Could not retrieve all users: ' + err);
        }

        res.send(collection);
    });
}

function update(req, res) {
    if (req.user._id === req.body._id || req.user.roles.indexOf('admin') > -1) {
        var user = req.body;
        if (user.password && user.password.length > 0) {
            var salt = userHelpers.generateSalt();
            user.salt = salt;
            var hash = userHelpers.hash(user.password, salt);
            user.password = hash;
        }

        User.findByIdAndUpdate(req.body._id, user, { new: true }, function (err, updatedUser) {
            res.send(updatedUser);
            res.end();
        });
    } else {
        res.status(400);
        res.send('Only the current user or an admin can change a profile.');
    }
}

module.exports = {
    create: create,
    all: all,
    update: update
}