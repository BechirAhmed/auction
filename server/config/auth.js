var passport = require('passport'),
    BasicStrategy = require('passport-http').BasicStrategy;

exports.isAuthenticated = function() {
    return passport.authenticate('basic', { session : false });
};