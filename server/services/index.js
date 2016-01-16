var fs = require('fs');
var fileNames = fs.readdirSync('../server/services/services');
var modules = {};

for (var i = 0, length = fileNames.length; i < length; i++) {
    var fileName = fileNames[i].substring(0, fileNames[i].length - 3);
    var curModule = require('./services/' + fileName);
    modules[fileName] = curModule;
}

module.exports = modules;