var passport = require('passport'),
    BasicStrategy = require('passport-http').BasicStrategy,
    User = require('mongoose').model('User');

module.exports = function () {
    passport.use(new BasicStrategy(
        function(username, password, done) {
            console.log(username);
            console.log(password);
            User.findOne({ username: username }, function (err, user) {
                if (err) {
                    return done(err);
                }

                if (user && user.authenticate(password)) {
                    return done(null, user);
                } else {
                    return done(null, false);
                }
            });
        }
    ));
};