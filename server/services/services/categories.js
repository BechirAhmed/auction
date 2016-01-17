var Category = require('mongoose').model('Category'),
    Product = require('mongoose').model('Product');

module.exports = {
    getAll: function () {
        var promise = new Promise(function (resolve, reject) {
            Category.find({}, function (err, categories) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(categories);
                }
            })

        });

        return promise;
    },
    add: function (category) {
        var promise = new Promise(function (resolve, reject) {
            Category.create(category, function (err, category) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(category);
                }
            })
        });

        return promise;
    },
    getCategoryByName: function (name) {
        var promise = new Promise(function (resolve, reject) {
            Category.find({ name: name }, function (err, category) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(category);
                }
            })

        });

        return promise;
    },
    getCategoryById: function (id) {
        var promise = new Promise(function (resolve, reject) {
            Category.findOne({ _id: id }, function (err, category) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(category);
                }
            })

        });

        return promise;
    },
    update: function (id, category) {
        var promise = new Promise(function (resolve, reject) {
            Category.update({_id: id}, category, function (err, categories) {
                if (err) {
                    reject(err);
                }
                else {
                    resolve(categories);
                }
            });
        });

        return promise;
    },
    getProductsByCategoryId: function (id) {
        var promise = new Promise(function (resolve, reject) {
            Category.findOne({_id: id})
                .populate({
                    path: 'products'
                })
                .exec(function (err, category) {
                    if (err) {
                        reject(err);
                    }
                    else {
                        resolve(category.products);
                    }
                });
        });

        return promise;
    },
    getCountOfProductsbyCategoryId: function(id) {
        var promise = new Promise(function (resolve, reject) {
                Category.findOne({_id: id})
                    .populate('products')
                    .exec(function (err, category) {
                        if (err) {
                            reject(err);
                        }
                        else {
                            resolve(category.products.length);
                        }
                    });
        });

        return promise;
    },
    removeCategoryById: function (id) {
        var promise = new Promise(function (resolve, reject) {
            Category.find({_id: id}).remove(function (err, removedCount) {
                if (err) {
                    reject(err);
                } else {
                    resolve(removedCount);
                }
            });
        });

        return promise;
    }
};
