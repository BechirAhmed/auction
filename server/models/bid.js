var mongoose = require('mongoose');

module.exports.init = function () {
    var bidSchema = new mongoose.Schema({
        itemId: { type: mongoose.Schema.Types.ObjectId, ref: 'Item' },
        amount: Number,
        createdBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
        createdOn: { type: Date, default: Date.now }
    });

    var Bid = mongoose.model('Bid', bidSchema);
};