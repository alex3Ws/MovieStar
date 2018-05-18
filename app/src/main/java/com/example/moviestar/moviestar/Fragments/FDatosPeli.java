package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Timer;
import java.util.TimerTask;

public class FDatosPeli extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    View vista;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView titulo_original,compañias,generos,pais,actores,sinopsis;
    JSONObject jsonObject;
    FloatingActionButton fbFav,fbTime,fbEye;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_datos_peli, container, false);


        fbFav = vista.findViewById(R.id.fbFavorite);
        fbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(fbFav.getTag().equals("0")) {
                    fbFav.setImageResource(R.drawable.star);
                    fbFav.setTag("1");
                }
                else{
                    fbFav.setImageResource(R.drawable.favorite);
                    fbFav.setTag("0");
                }
            }
        });

        fbTime = vista.findViewById(R.id.fbTimer);
        fbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fbTime.getTag().equals("0")) {
                    fbTime.setImageResource(R.drawable.checked);
                    fbTime.setTag("1");
                }
                else{
                    fbTime.setImageResource(R.drawable.clock);
                    fbTime.setTag("0");
                }

            }
        });


        fbEye = vista.findViewById(R.id.fbEyes);
        fbEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fbEye.getTag().equals("0")) {
                    fbEye.setImageResource(R.drawable.eye_blocked);
                    fbEye.setTag("1");
                }
                else{
                    fbEye.setImageResource(R.drawable.eye);
                    fbEye.setTag("0");
                }
            }

        });


        try {
              String jsonString = getArguments().getString("jsonObject");
              jsonObject = new JSONObject(jsonString);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        request = Volley.newRequestQueue(getContext());

        titulo_original= vista.findViewById(R.id.tvTituloOriginal2);
        compañias = vista.findViewById(R.id.tvCompañias2);
        generos = vista.findViewById(R.id.tvGenero2);
        pais = vista.findViewById(R.id.tvPais2);
        sinopsis = vista.findViewById(R.id.tvSinopsis2);
        actores = vista.findViewById(R.id.tvActores2);

        cargarDatos();

        return vista;
    }

    private void cargarDatos() {


        if(jsonObject.has("original_title") && !jsonObject.optString("original_title").equals("") && jsonObject.optString("original_title") != null) {
            titulo_original.setText(jsonObject.optString("original_title"));
        }else{
            titulo_original.setText("Unknown");
        }

        JSONArray jsonArrayComp = jsonObject.optJSONArray("production_companies");


        int i;
        String texto = "";
        JSONObject json;

        try {

            if(jsonArrayComp.length() > 0) {
                for (i = 0; i < jsonArrayComp.length(); i++) {

                    json = null;
                    json = jsonArrayComp.getJSONObject(i);

                    if (i != (jsonArrayComp.length() - 1))
                        texto = texto + json.optString("name") + ", ";
                    else
                        texto = texto + json.optString("name");
                }

                compañias.setText(texto);
            }else {
                compañias.setText("Unknown");
            }
        } catch(JSONException e){
            e.printStackTrace();
            compañias.setText("No se han podido recuperar las compañias");
        }



        JSONArray jsonArrayGen = jsonObject.optJSONArray("genres");
        texto = "";

        try {
            if(jsonArrayGen.length() > 0) {
                for (i = 0; i < jsonArrayGen.length(); i++) {

                    json = null;
                    json = jsonArrayGen.getJSONObject(i);

                    if (i != (jsonArrayGen.length() - 1))
                        texto = texto + json.optString("name") + ", ";
                    else
                        texto = texto + json.optString("name");

                }

                generos.setText(texto);

            }else{
               generos.setText("Unknown");
            }



        } catch(JSONException e){
            e.printStackTrace();
            generos.setText("No se han podido recuperar los géneros");
        }

        JSONArray jsonArrayPais = jsonObject.optJSONArray("production_countries");
        texto = "";

        try {
            if(jsonArrayPais.length() > 0) {
                for (i = 0; i < jsonArrayPais.length(); i++) {

                    json = null;
                    json = jsonArrayPais.getJSONObject(i);

                    if (i != (jsonArrayPais.length() - 1))
                        texto = texto + json.optString("name") + ", ";
                    else
                        texto = texto + json.optString("name");

                }

                pais.setText(texto);

            }else{
                pais.setText("Unknown");
            }


        } catch(JSONException e){
            e.printStackTrace();
            generos.setText("No se han podido recuperar los paises");
        }

        if(jsonObject.has("overview") && !jsonObject.optString("overview").equals("") && jsonObject.optString("overview") != null) {
            sinopsis.setText(jsonObject.optString("overview"));
        }
        else{
            sinopsis.setText("Unknown");
        }


        String url = "http://api.themoviedb.org/3/movie/"+jsonObject.optInt("id")+"/casts?api_key=a2424ed363ead46acaa726cf8cb45bad";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            String texto ="";
            int cont = 0;
            JSONArray jsonArray = response.optJSONArray("cast");
        if(jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = jsonArray.getJSONObject(i);

                if ((json.optInt("gender") == 1 || json.optInt("gender") == 2) && json.optString("profile_path") != null) {

                    if (i == (jsonArray.length() - 1) || cont == 14) {
                        texto = texto + json.optString("name");
                    } else {
                        texto = texto + json.optString("name") + ", ";
                    }
                    cont++;
                }

                if (cont == 15) {
                    i = jsonArray.length();
                }
            }
            actores.setText(texto);
        }else{
            actores.setText("Unknown");
        }




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Algo ha ido mal", Toast.LENGTH_SHORT).show();
    }

}
