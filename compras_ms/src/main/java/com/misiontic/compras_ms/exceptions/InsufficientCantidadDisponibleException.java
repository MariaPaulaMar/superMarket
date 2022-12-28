package com.misiontic.compras_ms.exceptions;

public class InsufficientCantidadDisponibleException extends RuntimeException {

    public InsufficientCantidadDisponibleException(String message) {
        super(message);
    }
}