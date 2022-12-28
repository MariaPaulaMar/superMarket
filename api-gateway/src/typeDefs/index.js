//Se llama al typedef (esquema) de cada submodulo
const accountTypeDefs = require('./account_type_defs');
const compraTypeDefs = require('./compra_type_defs');
const authTypeDefs = require('./auth_type_defs');
const productTypeDefs = require('./product_type_defs');

//Se unen
const schemasArrays = [authTypeDefs, accountTypeDefs, compraTypeDefs, productTypeDefs];

//Se exportan
module.exports = schemasArrays;