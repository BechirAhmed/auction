var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

module.exports.init = function () {
    var Product = require('mongoose').model('Product'),
        User = require('mongoose').model('User');

    var bidSchema = new mongoose.Schema({
        bidBy: {type: String, require: true},
        amount: { type: Number, require: true},
        productId: {type: Schema.Types.ObjectId, ref: 'Product'},
        bidDate: {type: Date, default: Date.now}
    });

    var Bid = mongoose.model('Bid', bidSchema);

    bidSchema.post('save', function (doc) {
        Product.findOne({'_id': doc.productId}).exec(function (err, product) {
            product.bids.push(doc.id);
            Product.update({ _id: product._id }, product, function (err, success) {
                if (err) {
                    console.log(err);
                    return;
                }
                User.findOne({username: doc.bidBy}).exec(function (err, user) {
                    user.bids.push(product);
                    User.update({_id: user._id}, user, function (err, success) {
                        if (err) {
                            console.log(err);
                            return;
                        }
                    })
                })
            });
        });
    });
};