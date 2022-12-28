const accountResolver = require('./account_resolver');
const compraResolver = require('./compra_resolver');
const authResolver = require('./auth_resolver');
const productResolver = require('./product_resolver');

const lodash = require('lodash');

const resolvers = lodash.merge(accountResolver, compraResolver, authResolver, productResolver);

module.exports = resolvers;
