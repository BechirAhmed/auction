var model = require('../models/item'),
    Item = require('mongoose').model('Item');

function create(req, res) {
    var newItem = new Item(req.body);
    newItem.save(function (err, savedItem) {
        if (err) {
            console.log('Could not create item: ' + err);
            res.status(400);
            res.send(err.toString());
        }

        res.send(savedItem);
    });
}

// TODO: Implement server-side paging here
function all(req, res) {
    Item.find({}).exec(function (err, collection) {
        if (err) {
            console.log('Could not retrieve all items: ' + err);
        }

        res.send(collection);
    });
}

function getById(req, res) {
    Item.findOne({ _id: req.params.id }).exec(function (err, item) {
        if (err) {
            console.log('Could not retrieve item: ' + err);
            res.status(400);
            res.end();
        }

        res.send(item);
    });
}

module.exports = {
    create: create,
    all: all,
    getById: getById
};
