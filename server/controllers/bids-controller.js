var services = require('../services');

module.exports = {
    getProductBids: function (req, res) {
        var productId = req.params.id;
        services.bids.getBidsByProductId(productId)
            .then(function (product) {
                res.send(product);
            })
    }
};