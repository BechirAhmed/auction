var services = require('../services');

module.exports = {
    getAll: function (req, res) {
        services.categories.getAll()
            .then(function (categories) {
                res.send(categories);
            }, function (err) {
                res.status(404)
                    .send(err);
            })
    },
    add: function (req, res) {
        var newCategory = req.body;
        services.categories.add(newCategory)
            .then(function (category) {
                res.send(category);
            }, function (err) {
                res.send(err);
            });

    },
    getProductsByCategoryId: function (req, res) {
        var id = req.params.id;

        services.categories.getProductsByCategoryId(id)
            .then(function (category) {
                res.send(category.products);
            }, function (err) {
                res.send(err);
            });
    }
};
