package com.example.cliente.Model;

public class ItemListOrder {
    private int Id;
    private String IdProducto;
    private String proDescripcion;
    private String cant;
    private String precio;

    public ItemListOrder() {
    }

    public ItemListOrder(int id,String idProducto, String proDescripcion, String cant, String precio) {
        Id = id;
        IdProducto = idProducto;
        this.proDescripcion = proDescripcion;
        this.cant = cant;
        this.precio = precio;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(String idProducto) {
        IdProducto = idProducto;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
