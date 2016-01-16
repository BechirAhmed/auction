var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    CategoryModel = require('./Category');

module.exports.init = function () {
    var Category = require('mongoose').model('Category'),
        User = require('mongoose').model('User');

    var productSchema = new mongoose.Schema({
        name: {type: String, require: true},
        price: {type: Number, require: true},
        currentPrice: {type: Number, require: true},
        imageUrl: {type: String},
        description: {type: String},
        postedDate: {type: Date, default: Date.now},
        categoryId: {type: Schema.Types.ObjectId, ref: 'Category'},
        postedBy: {type: String, require: true},
        bids: [{type: Schema.Types.ObjectId, ref: 'Bid'}]
    });

    productSchema.post('save', function (doc) {
        Category.findOne({'_id': doc.categoryId}).exec(function (err, category) {
            category.products.push(doc.id);
            Category.update({_id: category._id}, category, function (err, success) {
                if (err) {
                    console.log(err);
                    return;
                }

                User.findOne({username: doc.postedBy}).exec(function (err, user) {
                    user.postedProducts.push(doc.id);
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

    var Product = mongoose.model('Product', productSchema);
};
