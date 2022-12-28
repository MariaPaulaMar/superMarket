const { gql } = require('apollo-server');

const compraTypeDefs = gql `
    type Compra {
        id: String!
        username: String!
        nombreProducto: String!
        cantidad: Int!
        date: String!
    }
    
    input CompraInput {
        username: String!
        nombreProducto: String!
        cantidad: Int!
    }

    extend type Query {
        compraByNombreProducto(nombreProducto: String!): [Compra]
    }
    
    extend type Mutation {
        createCompra(compra: CompraInput!): Compra
        deleteCompra(id: String!): Compra
    }
`;

module.exports = compraTypeDefs;