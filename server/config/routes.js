var auth = require('./auth'),
    usersController = require('../controllers/users-controller'),
    itemsController = require('../controllers/items-controller'),
    bidsController = require('../controllers/bids-controller'),
    passport = require('passport');

module.exports = function (app) {
    app.post('/api/users', auth.isAuthenticated(), usersController.create);
    app.put('/api/users', auth.isAuthenticated(), usersController.update);

    app.post('/api/items', auth.isAuthenticated(), itemsController.create);
    app.get('/api/items', auth.isAuthenticated(), itemsController.all);
    app.get('/api/items/:id', auth.isAuthenticated(), itemsController.getById);

    app.get('/api/bids/:id', auth.isAuthenticated(), bidsController.getById);
    app.get('/api/items/:id/bids', auth.isAuthenticated(), bidsController.getByItemId);
    app.post('/api/items/:id/bids', auth.isAuthenticated(), bidsController.create);

    app.get('/api/*', function (req, res) {
        res.status(404);
        res.end();
    });

    app.get('*', function (req, res) {
        res.render('index');
    });
};
