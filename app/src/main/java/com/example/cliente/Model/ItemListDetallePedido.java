package com.example.cliente.Model;

public class ItemListDetallePedido {
    private String cantidad;
    private String descripcion;
    private String precio;

    public ItemListDetallePedido(String cantidad, String descripcion, String precio) {
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }
}
