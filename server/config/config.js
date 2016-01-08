var path = require('path');
var ROOT_PATH = path.normalize(__dirname + '/../../');
var PORT = process.env.PORT || 3000;

module.exports = {
    development: {
        dbConnectionString: 'mongodb://localhost/android-auction',
        ROOT_PATH: ROOT_PATH,
        PORT: PORT
    },
    production: {
        dbConnectionString: 'mongodb://localhost/android-auction',
        ROOT_PATH: ROOT_PATH,
        PORT: PORT
    }
};