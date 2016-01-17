var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

module.exports.init = function () {
    var categorySchema = new mongoose.Schema({
        name: {type: String, require: true, unique: true},
        products: [{type: Schema.Types.ObjectId, ref: 'Product'}]
    });

    categorySchema.virtual('id').get(function(){
        return this._id.toHexString();
    });

    // Ensure virtual fields are serialised.
    categorySchema.set('toJSON', {
        virtuals: true
    });

    var Category = mongoose.model('Category', categorySchema);

    Category.find({}).exec(function (err, collection) {
        if (err) {
            console.log('Cannot find categories: ' + err);
            return;
        }

        if (collection.length === 0) {
            Category.create({name: 'Cars'});
            Category.create({name: 'Computers'});
            Category.create({name: 'Pets'});
            Category.create({name: 'Books'});
            console.log('Seeded four categories.');
        }
    });

    categorySchema.pre('remove', function (next) {
        this.model('Product').remove({ categoryId: this._id }, next);
    });
};
