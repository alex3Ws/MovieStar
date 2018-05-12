package com.example.moviestar.moviestar.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pelicula implements Parcelable {

    private int id;
    private double valoracion;
    private String titulo;
    private String titulo_original;
    private String caratula = "http://image.tmdb.org/t/p/w500";
    private ArrayList<String> generos;
    private String sinopsis;
    private String ano;


    public Pelicula(){

    }

    public Pelicula(int id, double valoracion, String titulo, String titulo_original, String caratula, ArrayList<String> generos, String sinopsis, String ano) {
        this.id = id;
        this.valoracion = valoracion;
        this.titulo = titulo;
        this.titulo_original = titulo_original;
        this.caratula = this.caratula + caratula;
        this.generos = generos;
        this.sinopsis = sinopsis;
        this.ano = ano;
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

    public String getTitulo_original() {
        return titulo_original;
    }

    public void setTitulo_original(String titulo_original) {
        this.titulo_original = titulo_original;
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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }


    protected Pelicula(Parcel in) {
        id = in.readInt();
        valoracion = in.readDouble();
        titulo = in.readString();
        titulo_original = in.readString();
        caratula = in.readString();
        if (in.readByte() == 0x01) {
            generos = new ArrayList<String>();
            in.readList(generos, String.class.getClassLoader());
        } else {
            generos = null;
        }
        sinopsis = in.readString();
        ano = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(valoracion);
        dest.writeString(titulo);
        dest.writeString(titulo_original);
        dest.writeString(caratula);
        if (generos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(generos);
        }
        dest.writeString(sinopsis);
        dest.writeString(ano);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pelicula> CREATOR = new Parcelable.Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };
}