package com.example.cliente.adapter;

public class ModelAds {

    String titulo, descripcion,imagen,categoria,url,fechainicio,fechafinal,montopagado,id;

    public ModelAds(String titulo, String descripcion, String imagen, String categoria, String url, String fechainicio, String fechafinal, String montopagado,String id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria = categoria;
        this.url = url;
        this.fechainicio = fechainicio;
        this.fechafinal = fechafinal;
        this.montopagado = montopagado;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(String fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getMontopagado() {
        return montopagado;
    }

    public void setMontopagado(String montopagado) {
        this.montopagado = montopagado;
    }

    public String getId() {
        return id;
    }

    public void setId(String montopagado) {
        this.id = id;
    }


}
