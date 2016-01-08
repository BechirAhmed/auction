var express = require('express'),
    mongoose = require('mongoose'),
    bodyParser = require('body-parser'),
    morgan = require('morgan');

var env = process.env.NODE_ENV || 'development';
var app = express();
var config = require('./config/config')[env];

require('./config/express')(app, config);
require('./config/mongoose')(config);
require('./config/routes')(app);
require('./config/passport')();

app.listen(config.PORT);
console.log('Node.js server running on port ' + config.PORT);