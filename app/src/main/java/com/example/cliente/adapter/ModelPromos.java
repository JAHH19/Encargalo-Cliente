package com.example.cliente.adapter;

public class ModelPromos {
    String id,titulo, fechaini,fechafin,imagen,estado,idtienda,idcategoria,idproducto;

    public ModelPromos(String id,String titulo, String fechaini, String fechafin, String imagen, String estado, String idtienda, String idcategoria, String idproducto) {
        this.id = id;
        this.titulo = titulo;
        this.fechaini = fechaini;
        this.fechafin = fechafin;
        this.imagen = imagen;
        this.estado = estado;
        this.idtienda = idtienda;
        this.idcategoria = idcategoria;
        this.idproducto = idproducto;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaini() {
        return fechaini;
    }

    public void setFechaini(String fechaini) {
        this.fechaini = fechaini;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(String idtienda) {
        this.idtienda = idtienda;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }
}
