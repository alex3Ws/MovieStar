package com.example.moviestar.moviestar.Entidades;

public class Grupo {

    private int id_grupo;
    private String nombre_grupo;
    private String fecha;
    private String urlImagen;
    private int numero_participantes;

    public Grupo(){

    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getNumero_participantes() {
        return numero_participantes;
    }

    public void setNumero_participantes(int numero_participantes) {
        this.numero_participantes = numero_participantes;
    }
}
