var mongoose = require('mongoose'),
    userHelpers = require('../helpers/user-helpers');

module.exports.init = function () {
    var userSchema = mongoose.Schema({
        username: { type: String, require: '{PATH} is required', unique: true },
        password: String,
        salt: String,
        roles: [String]
    });

    userSchema.method({
        authenticate: function (password) {
            if (userHelpers.hash(password, this.salt) === this.password) {
                return true;
            } else {
                return false;
            }
        }
    });

    var User = mongoose.model('User', userSchema);
};