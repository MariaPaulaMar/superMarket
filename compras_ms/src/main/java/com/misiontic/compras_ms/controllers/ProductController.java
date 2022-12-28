package com.misiontic.compras_ms.controllers;

import com.misiontic.compras_ms.exceptions.ProductNotFoundException;
import com.misiontic.compras_ms.models.Compra;
import com.misiontic.compras_ms.models.Product;
import com.misiontic.compras_ms.repositories.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/{nombreProducto}")
    Product getProduct(@PathVariable String nombreProducto){
        return productRepository.findById(nombreProducto).orElseThrow(() -> new ProductNotFoundException("No se encontro un producto con el nombre: " + nombreProducto));
    }
    @PostMapping("/products")
    Product newProduct(@RequestBody Product product){
        return productRepository.save(product);
    }
}
