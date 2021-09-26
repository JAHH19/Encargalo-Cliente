package com.example.cliente.Model;

import java.io.Serializable;

public class ItemListEsta implements Serializable {

    private String id_tienda,nom_tienda,ubicacion,calific;
    private int imag_tienda;

    public ItemListEsta(String idtienda, String nom_tienda, String ubicacion, int imag_tienda, String calific){
        this.id_tienda=idtienda;
        this.nom_tienda = nom_tienda;
        this.ubicacion=ubicacion;
        this.imag_tienda=imag_tienda;
        this.calific=calific;

    }
    public String getId_tienda(){return id_tienda;}
    public String getNom_tienda(){
        return nom_tienda;
    }
    public String getUbicacion(){
        return ubicacion;
    }
    public int getImag_tienda(){
        return imag_tienda;
    }
    public String getCalific(){
        return calific;
    }

}