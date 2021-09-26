package com.example.cliente.Model;

import java.io.Serializable;

public class ItemListHistorialPedidos implements Serializable {
    private String npedido, fecha, nomTienda, total, estado,idpedido;
    private int imgTienda;

    public ItemListHistorialPedidos(String npedido, String fecha, String nomTienda, String total, String estado, int imgTienda, String idpedido) {
        this.npedido = npedido;
        this.fecha = fecha;
        this.nomTienda = nomTienda;
        this.total = total;
        this.estado = estado;
        this.imgTienda = imgTienda;
        this.idpedido = idpedido;
    }

    public String getNpedido() {
        return npedido;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNomTienda() {
        return nomTienda;
    }

    public String getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public int getImgTienda() {
        return imgTienda;
    }
    public String getIdpedido(){
        return idpedido;
    }
}
