var Product = require('mongoose').model('Product');

module.exports = {
    add: function (product) {
        var promise = new Promise(function (resolve, reject) {
            Product.create(product, function (err, product) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(product);
                }
            });
        });

        return promise;
    },
    getProductById: function (id) {
        var promise = new Promise(function (resolve, reject) {
            Product.findOne({_id : id}, function (err, product) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(product);
                }
            })

        });

        return promise;
    },
    deleteProductById: function (id) {
        var promise = new Promise(function (resolve, reject) {
            Product.remove({_id : id}, function (err) {
                if (err) {
                    reject(err);
                } else {
                    resolve();
                }
            });
        });

        return promise;
    },
    getFirstNProducts: function (numberOfProducts) {
        var promise = new Promise(function (resolve, reject) {
            Product.find({})
                .limit(numberOfProducts)
                .exec(function (err, products) {
                    if (err) {
                        reject(err);
                    }
                    else {
                        resolve(products);
                    }
                });
        });

        return promise;
    }
};