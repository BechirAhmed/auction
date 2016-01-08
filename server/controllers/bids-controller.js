var model = require('../models/item'),
    Item = require('mongoose').model('Item'),
    Bid = require('mongoose').model('Bid');

function create(req, res) {
    var newBid = new Bid();
    newBid.amount = req.amount;
    newBid.itemId = req.params.id;
    newBid.createdBy = undefined;
    console.log(req.user);
    /*
    newBid.save(function (err, savedBid) {
        if (err) {
            console.log('Could not create bid: ' + err);
            res.status(400);
            res.send(err.toString());
        }

        res.send(savedBid);
    });
    */
}

function all(req, res) {
    Bid.find({}).exec(function (err, collection) {
        if (err) {
            console.log('Could not retrieve all bids: ' + err);
        }

        res.send(collection);
    });
}

function getById(req, res) {
    Bid.findOne({ _id: req.params.id }).exec(function (err, item) {
        if (err) {
            console.log('Could not retrieve bid details: ' + err);
            res.status(400);
            res.end();
        }

        res.send(item);
    });
}

function getByItemId(req, res) {
    Bid.find({ itemId: req.params.id }).exec(function (err, collection) {
        if (err) {
            console.log("Could not retrieve item's bids: " + err);
            res.status(400);
            res.end();
        }

        res.send(collection);
    });
}

module.exports = {
    create: create,
    all: all,
    getById: getById,
    getByItemId: getByItemId
};
