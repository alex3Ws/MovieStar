package com.example.moviestar.moviestar.Entidades;

public class Amigo {

    private String urlImagen;
    private String nombreUsuario;
    private String Pais;

    public Amigo() {
    }

    public Amigo(String urlImagen, String nombreUsuario, String pais) {
        this.urlImagen = urlImagen;
        this.nombreUsuario = nombreUsuario;
        Pais = pais;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = this.urlImagen + urlImagen;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }
}
