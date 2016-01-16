var mongoose = require('mongoose'),
    UserModel = require('../models/User'),
    CategoryModel = require('../models/Category'),
    ProductModel = require('../models/Product'),
    BidModel = require('../models/Bid');

module.exports = function (config) {
    mongoose.connect(config.dbConnectionString);
    var db = mongoose.connection;

    db.once('open', function (err) {
        if (err) {
            console.log(err);
            return;
        }

        console.log('Database up and running!');
    });

    db.on('error', function (err) {
        console.log(err);
    });

    UserModel.init();
    CategoryModel.init();
    ProductModel.init();
    BidModel.init();

    /*
    // Seed initial items
    var dogItem = new Item({
        name: 'Prodavam nemska ovcharka',
        description: 'Chistak nova, palish i karash :)',
        imageLink: 'http://i.imgur.com/EJQ7Wj3.gif',
        location: 'Sofia',
        ends: new Date(2015, 12, 28, 16, 30, 0, 0),
        createdOn: new Date(),
        createdBy:
    });
    dogItem.save();

    var speakersItem = new Item({
        name: 'Kolonki Creative vtora ruka',
        description: 'Super dobrite napravo kurtat bloka',
        imageLink: 'http://i.imgur.com/EJQ7Wj3.gif',
        location: 'Pernik',
        ends: new Date(2015, 12, 28, 16, 30, 0, 0)
    });
    speakersItem.save();

    var bookItem = new Item({
        name: 'Uchebnik po istoriq',
        description: 'Chisto nov, gledam da se oturva che ne mi se uchi',
        imageLink: 'http://i.imgur.com/EJQ7Wj3.gif',
        location: 'Varna',
        ends: new Date(2015, 12, 28, 16, 30, 0, 0)
    });
    bookItem.save();
    */
};
