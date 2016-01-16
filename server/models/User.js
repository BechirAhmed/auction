var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    userHelpers = require('../helpers/user-helpers');

module.exports.init = function () {
    var userSchema = new mongoose.Schema({
        username: {type: String, require: '{PATH} is required', unique: true},
        salt: String,
        hashPass: String,
        postedProducts: [{type: Schema.Types.ObjectId, ref: 'Product'}],
        roles: [{type: String}],
        email: {type: String},
        firstName: {type: String},
        lastName: {type: String},
        bids: [{type: Schema.Types.ObjectId, ref: 'Bid'}],
        avatar: String
    });

    userSchema.method({
        authenticate: function (password) {
            if (userHelpers.hash(password, this.salt) === this.hashPass) {
                return true;
            }
            else {
                return false;
            }
        }
    });

    var User = mongoose.model('User', userSchema);

    // Seed users
    User.find({}).exec(function (err, collection) {
        if (err) {
            console.log('Cannot find users: ' + err);
            return;
        }

        if (collection.length === 0) {
            var salt;
            var hashedPwd;

            salt = userHelpers.generateSalt();
            hashedPwd = userHelpers.hash('testtest', salt);
            User.create({
                username: 'ivaylo.arnaudov',
                email: 'ivaylo@arnaudov.com',
                salt: salt,
                hashPass: hashedPwd,
                firstName: 'Ivaylo',
                lastName: 'Arnaudov',
                roles: ['admin']
            });

            console.log('Seeded a user to the db.');
        }
    });
};
