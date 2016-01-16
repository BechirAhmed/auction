var Bid = require('mongoose').model('Bid'),
    Product = require('mongoose').model('Product');

module.exports = {
    create: function (order) {
        var promise = new Promise(function (resolve, reject) {
            Bid.create(order, function (err, order) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(order);
                }
            })

        });

        return promise;
    },
    getBidsByProductId: function (productId) {
        var promise = new Promise(function (resolve, reject) {
            Product.findOne({_id: productId})
                .populate({
                    path: 'bids',
                    options: {limit: 10} // TODO: sort by date desc
                })
                .exec(function (err, product) {
                    if (err) {
                        reject(err);
                    }
                    else {

                        resolve(product.bids);
                    }
                })
        });

        return promise;
    },
    getBidsByUsername: function (username, callback) {
        var promise = new Promise(function (resolve, reject) {
            Bid.find({ orderedBy: username })
                .populate('productId')
                .exec(function (err, orders) {
                    if (err) {
                        reject(err);
                    } else {
                        resolve(orders);
                    }
                });
        });

        return promise;
    }
};