package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FDatosPeli extends Fragment {

    View vista;
    Pelicula pelicula;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView titulo_original,compañias,generos,pais,actores,sinopsis;
    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_datos_peli, container, false);

        try {

            String jsonString = getArguments().getString("jsonObject");
            jsonObject = new JSONObject(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_original= vista.findViewById(R.id.tvTituloOriginal2);
        compañias = vista.findViewById(R.id.tvCompañias2);
        generos = vista.findViewById(R.id.tvGenero2);
        pais = vista.findViewById(R.id.tvActores2);
        sinopsis = vista.findViewById(R.id.tvSinopsis2);

        cargarDatos();

        return vista;
    }

    private void cargarDatos() {

        titulo_original.setText(jsonObject.optString("original_title"));

        JSONArray jsonArrayComp = jsonObject.optJSONArray("production_companies");


        int i;
        String texto = "";
        JSONObject json;

        try {

            for (i = 0; i < jsonArrayComp.length(); i++) {

                json = null;
                json = jsonArrayComp.getJSONObject(i);

                texto = texto + json.optString("name") + ", ";
            }

            compañias.setText(texto);

        } catch(JSONException e){
            e.printStackTrace();
            compañias.setText("No se han podido recuperar las compañias");
        }



        JSONArray jsonArrayGen = jsonObject.optJSONArray("genres");
        texto = "";

        try {

            for(i=0;i<jsonArrayGen.length();i++){

                json = null;
                json = jsonArrayGen.getJSONObject(i);

                texto = texto + json.optString("name") + ", ";

            }

            generos.setText(texto);

        } catch(JSONException e){
            e.printStackTrace();
            generos.setText("No se han podido recuperar los géneros");
        }

        JSONArray jsonArrayPais = jsonObject.optJSONArray("genres");
        texto = "";

        try {

            for(i=0;i<jsonArrayPais.length();i++){

                json = null;
                json = jsonArrayPais.getJSONObject(i);

                texto = texto + json.optString("name") + ", ";

            }

            pais.setText(texto);

        } catch(JSONException e){
            e.printStackTrace();
            generos.setText("No se han podido recuperar los paises");
        }


        sinopsis.setText(jsonObject.optString("overview"));




    }


}
