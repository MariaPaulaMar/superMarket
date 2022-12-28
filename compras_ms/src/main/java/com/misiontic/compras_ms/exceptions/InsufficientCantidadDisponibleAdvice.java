package com.misiontic.compras_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class InsufficientCantidadDisponibleAdvice {
    @ResponseBody
    @ExceptionHandler(InsufficientCantidadDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String InsufficientCantidadDisponibleAdvice(InsufficientCantidadDisponibleException ex){
        return ex.getMessage();
    }
}