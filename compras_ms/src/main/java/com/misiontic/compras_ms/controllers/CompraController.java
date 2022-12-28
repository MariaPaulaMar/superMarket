package com.misiontic.compras_ms.controllers;

import com.misiontic.compras_ms.exceptions.ProductNotFoundException;
import com.misiontic.compras_ms.exceptions.InsufficientCantidadDisponibleException;
import com.misiontic.compras_ms.exceptions.ProductCantidadInvalida;
import com.misiontic.compras_ms.exceptions.AccountNotFoundException;
import com.misiontic.compras_ms.models.Product;
import com.misiontic.compras_ms.models.Compra;
import com.misiontic.compras_ms.models.Account;
import com.misiontic.compras_ms.repositories.AccountRepository;
import com.misiontic.compras_ms.repositories.ProductRepository;
import com.misiontic.compras_ms.repositories.CompraRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CompraController {

    private final ProductRepository productRepository;
    private final CompraRepository compraRepository;
    private final AccountRepository accountRepository;

    public CompraController(ProductRepository productRepository, CompraRepository compraRepository, AccountRepository accountRepository) {
        this.productRepository = productRepository;
        this.compraRepository = compraRepository;
        this.accountRepository = accountRepository;

    }

    @PostMapping("/compras")
    Compra nuevaCompra(@RequestBody Compra compra){
        Account user = accountRepository.findById(compra.getUsername()).orElse(null);
        Product product= productRepository.findById(compra.getNombreProducto()).orElse(null);

        if (user == null){
            throw new AccountNotFoundException("No se encontro una cuenta con el username: " + compra.getUsername());
        }
        if(compra.getCantidad()>0){
            if(product.getCantidadDisponible()>=compra.getCantidad()) {
                product.setCantidadDisponible(product.getCantidadDisponible() - compra.getCantidad());
            }
        }else {
            throw new ProductCantidadInvalida("La cantidad solicitada del producto debe ser valida" + compra.getCantidad());
        }
        if(product.getPrecio()* compra.getCantidad()>user.getBalance()){
            throw new ProductCantidadInvalida("El saldo de su cuenta " + user.getBalance() +"es menor que el valor a pagar.");
        }else{
            user.setBalance(user.getBalance()-(product.getPrecio()* compra.getCantidad()));
        }
        productRepository.save(product);
        accountRepository.save(user);

        compra.setDate(new Date());

        return compraRepository.save(compra);
    }


    @GetMapping("/compras/{nombreProducto}")
    List<Compra> userCompra(@PathVariable String nombreProducto){
        Product userProduct = productRepository.findById(nombreProducto).orElse(null);

        if (userProduct == null)
            throw new ProductNotFoundException("No se encontro producto llamado: " + nombreProducto);

        List<Compra> comprasOrigin = compraRepository.findByUsername(nombreProducto);
        List<Compra> comprasNombreProducto = compraRepository.findByNombreProducto(nombreProducto);

        List<Compra> compras = Stream.concat(comprasOrigin.stream(), comprasNombreProducto.stream()).collect(Collectors.toList());

        return compras;
    }
    @DeleteMapping("/compras/{id}")
    public Compra eliminarCompra(@PathVariable String id){
        Compra compra = compraRepository.findById(id).orElse(null);

        compraRepository.delete(compra);

        return compra;
    }
}