package com.example.cliente.Model;

import java.io.Serializable;

public class ItemListProductos implements Serializable {
    private String descripcion;
    private String precio;
    private String idproducto;
    private int imgProducto;

    public ItemListProductos(String idproducto, String descripcion, String precio, int imgProducto){
        this.idproducto = idproducto;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imgProducto = imgProducto;
    }
    public String getIdproducto(){
        return idproducto;
    }
    public String getPrecio(){
        return precio;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public int getImgProducto(){
        return imgProducto;
    }
}
