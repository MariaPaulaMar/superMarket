package com.misiontic.compras_ms.models;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Compra {
    @Id
    private String id;
    private String username;
    private String nombreProducto;
    private Integer cantidad;
    private Date date;

    public Compra(String id, String username, String nombreProducto, Integer cantidad, Date date) {
        this.id = id;
        this.username = username;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}