var express = require('express'),
    morgan = require('morgan'),
    cookieParser = require('cookie-parser'),
    bodyParser = require('body-parser'),
    session = require('express-session'),
    passport = require('passport');

module.exports = function (app, config) {
    app.use(morgan('combined'));
    app.use(cookieParser());
    app.use(bodyParser.urlencoded({
        extended: true
    }));
    app.use(bodyParser.json());
    app.use(session({
        secret: 'kek',
        resave: true,
        saveUninitialized: true
    }));
    app.use(passport.initialize());
    app.use(express.static(config.ROOT_PATH + '/public'));
};
