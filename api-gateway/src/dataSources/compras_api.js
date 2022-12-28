const { RESTDataSource } = require('apollo-datasource-rest');

const serverConfig = require('../server');

class ComprasAPI extends RESTDataSource {

    constructor() {
        super();
        this.baseURL = serverConfig.compras_api_url;
    }

    async createAccount(account) {
        account = new Object(JSON.parse(JSON.stringify(account)));
        return await this.post('/accounts', account);
    }

    async accountByUsername(username) {
        return await this.get(`/accounts/${username}`);
    }

    async createProduct(product) {
        product = new Object(JSON.parse(JSON.stringify(product)));
        return await this.post('/products', product);
    }

    async productByNombreProducto(nombreProducto) {
        return await this.get(`/products/${nombreProducto}`);
    }

    async createCompra(compra) {
        compra = new Object(JSON.parse(JSON.stringify(compra)));
        return await this.post('/compras', compra);
    }

    async compraById(nombreProducto) {
        return await this.get(`/compras/${nombreProducto}`);
    }

    async deleteCompra(id) {
        return await this.delete(`/compras/${id}`);
    }
}

module.exports = ComprasAPI;