package com.example.cliente.Model;

public class ItemListCategoria {
    private  String categoria;
    private String IdTienda;

    public ItemListCategoria(String IdTienda, String categoria){
        this.categoria = categoria;
        this.IdTienda = IdTienda;
    }
    public String getCategoria(){
        return  categoria;
    }
    public String getIdTienda(){
        return IdTienda;
    }
}