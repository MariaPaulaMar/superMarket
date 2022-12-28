const compraResolver = {
    Query: {
        compraByNombreProducto: async (_, { nombreProducto }, { dataSources }) => {
            return dataSources.comprasAPI.compraByNombreProducto(nombreProducto)
        }
    },
    Mutation: {
        createCompra: async (_, { compra }, { dataSources, userIdToken }) => {
            usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
            if (compra.username == usernameToken)
                return dataSources.comprasAPI.createCompra(compra)
            else
                return null
        },
        
        deleteCompra: async (_, { id }, { dataSources }) => {
            return dataSources.comprasAPI.deleteCompra(id) 
        }
    }
};

module.exports = compraResolver;