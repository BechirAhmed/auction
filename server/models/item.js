var mongoose = require('mongoose');

module.exports.init = function () {
    var itemSchema = mongoose.Schema({
        name: { type: String, require: '{PATH} is required'},
        description: { type: String, require: '{PATH} is required' },
        imageLink: String,
        location: String,
        ends: Date,
        createdOn: { type: Date, default: Date.now },
        createdBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' }
    });

    var Item = mongoose.model('Item', itemSchema);
};