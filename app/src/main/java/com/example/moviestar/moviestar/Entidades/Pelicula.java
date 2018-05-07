package com.example.moviestar.moviestar.Entidades;

import java.util.ArrayList;

public class Pelicula {

    private int id;
    private double valoracion;
    private String titulo;
    private String caratula = "http://image.tmdb.org/t/p/w92";
    private ArrayList<String> generos;
    private String sinopsis;
    private String año;

    public Pelicula(){

    }

    public Pelicula(int id, double valoracion, String titulo, String caratula, ArrayList<String> generos, String sinopsis, String año) {
        this.id = id;
        this.valoracion = valoracion;
        this.titulo = titulo;
        this.caratula = caratula;
        this.generos = generos;
        this.sinopsis = sinopsis;
        this.año = año;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = this.caratula + caratula;
    }

    public ArrayList<String> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<String> generos) {
        this.generos = generos;

    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }
}
