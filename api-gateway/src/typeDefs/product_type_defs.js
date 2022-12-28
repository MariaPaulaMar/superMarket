const { gql } = require('apollo-server');

const productTypeDefs = gql `
    type Product {
        nombreProducto: String!
        descripcion: String!
        precio: Int!
        cantidadDisponible: Int!
        lastChange: String!
    }
    
    input ProductInput {
        nombreProducto: String!
        descripcion: String!
        precio: Int!
        cantidadDisponible: Int!
    }

    extend type Query {
        productByNombreProducto(nombreProducto: String!): Product
    }
    
    extend type Mutation {
        createProduct(product: ProductInput!): Product
    }
`;

module.exports = productTypeDefs;