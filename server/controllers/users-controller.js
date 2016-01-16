var User = require('mongoose').model('User'),
    userHelpers = require('../helpers/user-helpers'),
    services = require('../services');

module.exports = {
    create: function (req, res) {
        var user = req.body;
        var salt = userHelpers.generateSalt();
        user.salt = salt;
        var hashPass = userHelpers.hash(user.password, salt);
        user.hashPass = hashPass;

        User.create(user, function (err, user) {
            if (err) {
                console.log('Could not register user: ' + err);
                res.send({success: false});
                return;
            } else {
                res.send(user);
            }
        });
    },
    all: function all(req, res) {
        User.find({}).exec(function (err, collection) {
            if (err) {
                console.log('Could not retrieve all users: ' + err);
                res.send({success: false});
            }

            res.send(collection);
        });
    },
    update: function (req, res) {
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
};