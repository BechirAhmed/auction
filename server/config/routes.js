var auth = require('./auth'),
    usersController = require('../controllers/users-controller'),
    productsController = require('../controllers/products-controller'),
    categoriesController = require('../controllers/categories-controller'),
    bidsController = require('../controllers/bids-controller'),
    passport = require('passport');

module.exports = function (app) {
    app.post('/api/users', usersController.create);
    app.put('/api/users', auth.isAuthenticated(), usersController.update);

    app.get('/api/users/:username/products', auth.isAuthenticated(), productsController.getUserProductsByUsername);
    app.get('/api/products/:id', auth.isAuthenticated(), productsController.getProductDetails);
    app.get('/api/products/:id/bids', auth.isAuthenticated(), bidsController.getProductBids);
    app.post('/api/products/:id', auth.isAuthenticated(), productsController.bidOnProduct);

    app.get('/api/categories', auth.isAuthenticated(), categoriesController.getAll);
    app.get('/api/categories/:id/products', auth.isAuthenticated(), categoriesController.getProductsByCategoryId);
    app.post('/api/categories/:id/products', auth.isAuthenticated(), productsController.add);


    app.get('/api/*', function (req, res) {
        res.status(404);
        res.end();
    });

    app.get('*', function (req, res) {
        res.status(404);
        res.end();
    });
};
