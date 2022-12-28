const productResolver = {
    Query: {
        productByNombreProducto: async (_, { nombreProducto }, { dataSources }) => {
            return dataSources.comprasAPI.productByNombreProducto(nombreProducto)
        }
    },
    Mutation: {
        createProduct: async (_, { product }, { dataSources }) => {
            return dataSources.comprasAPI.createProduct(product)
        }
    }
};

module.exports = productResolver;