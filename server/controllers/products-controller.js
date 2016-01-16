var services = require('../services');

module.exports = {
    add: function (req, res) {
        var newProduct = req.body;
        newProduct.postedBy = req.user.username;
        newProduct.categoryId = req.params.id;

        services.products.add(newProduct).then(function (product) {
            res.send(product);
        }, function (err) {
            req.status(404)
                .send(err);
        })

    },
    getProductDetails: function (req, res) {
        services.products.getProductById(req.params.id)
            .then(function (product) {
                res.send(product);
            }, function (err) {
                res.status(404)
                    .send(err);
            });
    },
    bidOnProduct: function (req, res) {
        var productId = req.params.id;
        var bid = req.body;
        bid.productId = productId;
        services.products.getProductById(productId)
            .then(function (product) {
                if (product.postedBy == req.user.username) {
                    res.status(404)
                        .send({error: 'You cannot bid on products that you posted.'});
                }
                else {
                    bid.bidBy = req.user.username;
                    services.bids.create(bid)
                        .then(function (responseOrder) {
                            res.send(responseOrder);
                        }, function (err) {
                            console.log(err);
                        })
                }
            });
    },
    getUserProductsByUsername: function (req, res) {
        services.users.getUserProductsByUsername(req.params.username)
            .then(function (products) {
                res.send(products);
            }, function (err) {
                res.send({success: false});
            });
    }
};