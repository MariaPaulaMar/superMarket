package com.misiontic.compras_ms.repositories;

import com.misiontic.compras_ms.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository <Product, String> { }
