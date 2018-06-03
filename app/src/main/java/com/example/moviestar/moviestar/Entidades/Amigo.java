package com.example.moviestar.moviestar.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Amigo implements Parcelable {

    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    protected Amigo(Parcel in) {
        id = in.readInt();
        urlImagen = in.readString();
        nombreUsuario = in.readString();
        Pais = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(urlImagen);
        dest.writeString(nombreUsuario);
        dest.writeString(Pais);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Amigo> CREATOR = new Parcelable.Creator<Amigo>() {
        @Override
        public Amigo createFromParcel(Parcel in) {
            return new Amigo(in);
        }

        @Override
        public Amigo[] newArray(int size) {
            return new Amigo[size];
        }
    };
}
