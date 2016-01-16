var User = require('mongoose').model('User'),
    Product = require('mongoose').model('Product');

module.exports = {
    create: function (user) {
        var promise = new Promise(function (resolve, reject) {
            User.create(user, function (err, user) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(user);
                }
            })
        });

        return promise;
    },
    update: function (username, user) {
        var promise = new Promise(function (resolve, reject) {
            User.update({username: username}, user, function (err, done) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(done);
                }
            })
        });

        return promise;
    },
    getUserByUsername: function (username) {
        var promise = new Promise(function (resolve, reject) {
            User.findOne({ username: username}, function (err, user) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(user);
                }
            })

        });

        return promise;
    },
    getUserProductsByUsername: function (username) {
        var promise = new Promise(function (resolve, reject) {
            User.findOne({username: username})
                .populate('postedProducts')
                .exec(function (err, user) {
                    if (err) {
                        reject(err);
                    } else {
                        resolve(user.postedProducts);
                    }
                });
        });

        return promise;
    },
    getUserById: function (id) {
        var promise = new Promise(function (resolve, reject) {
            User.findOne({ _id: id }, function (err, user) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(user);
                }
            })

        });

        return promise;
    }
};