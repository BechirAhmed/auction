var usersService = require('./services/users'),
    categoriesService = require('./services/categories'),
    productsService = require('./services/products'),
    bidsService = require('./services/bids');

module.exports = {
    users: usersService,
    categories: categoriesService,
    products: productsService,
    bids: bidsService
}
