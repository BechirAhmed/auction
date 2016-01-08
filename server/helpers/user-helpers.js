var crypto = require('crypto');

function generateSalt() {
    return crypto.randomBytes(128).toString('base64');
}

function hash(password, salt) {
    var hmac = crypto.createHmac('sha1', salt);
    return hmac.update(password).digest('hex');
}

module.exports = {
    generateSalt: generateSalt,
    hash: hash
}