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
    JSONObject parametros;
    int user_id,id_pelicula;
    String operacion,tipo;

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

        id_pelicula = jsonObject.optInt("id");
        user_id = getArguments().getInt("user_id");

        fbFav = vista.findViewById(R.id.fbFavorite);
        fbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo = "favorita";

                if(fbFav.getTag().equals("0")) {
                    fbFav.setImageResource(R.drawable.star);
                    operacion = "insert";
                    fbFav.setTag("1");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo, false);


                }
                else{
                    fbFav.setImageResource(R.drawable.favorite);
                    operacion = "delete";
                    fbFav.setTag("0");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo,true);
                }
            }
        });

        fbTime = vista.findViewById(R.id.fbTimer);
        fbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo = "pendiente";

                if(fbTime.getTag().equals("0")) {
                    fbTime.setImageResource(R.drawable.checked);
                    operacion = "insert";
                    fbTime.setTag("1");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo, false);

                }
                else{
                    fbTime.setImageResource(R.drawable.clock);
                    operacion = "delete";
                    fbTime.setTag("0");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo, true);
                }

            }
        });


        fbEye = vista.findViewById(R.id.fbEyes);
        fbEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo = "vista";

                if(fbEye.getTag().equals("0")) {
                    fbEye.setImageResource(R.drawable.eye_blocked);
                    operacion = "insert";
                    fbEye.setTag("1");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo, false);

                }
                else{
                    fbEye.setImageResource(R.drawable.eye);
                    operacion = "delete";
                    fbEye.setTag("0");

                    parametros = null;
                    parametros = new JSONObject();
                    try {
                        parametros.put("id_user", user_id);
                        parametros.put("operacion", operacion);
                        parametros.put("id_pelicula", id_pelicula);
                        parametros.put("tipo", tipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    peliculas_user(parametros,tipo, true);

                }
            }

        });



        request = Volley.newRequestQueue(getContext());

        titulo_original= vista.findViewById(R.id.tvTituloOriginal2);
        compañias = vista.findViewById(R.id.tvCompañias2);
        generos = vista.findViewById(R.id.tvGenero2);
        pais = vista.findViewById(R.id.tvPais2);
        sinopsis = vista.findViewById(R.id.tvSinopsis2);
        actores = vista.findViewById(R.id.tvActores2);

        cargarDatos();

        recuperarPreferenciasUsuario();

        return vista;
    }

    //Cargar datos de la pelicula
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

    //Llamada a WS para añadir o borrar preferencias del usuario(FAVORITAS,PENDIENTES O VISTAS)
    public void peliculas_user(JSONObject parametros, final String tipo, final Boolean estado){

        String url = getString(R.string.url);
        url = url + "/Peliculas_user.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getContext(),response.optString("respuesta"),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Se ha producido un error",Toast.LENGTH_SHORT).show();

                if(estado){

                    switch (tipo){

                        case "favorita":    fbFav.setImageResource(R.drawable.favorite);
                                            fbFav.setTag("0");
                                            break;

                        case "pendiente":   fbTime.setImageResource(R.drawable.checked);
                                            fbTime.setTag("0");
                                            break;

                        case "vista":       fbEye.setImageResource(R.drawable.eye_blocked);
                                            fbEye.setTag("0");
                                            break;
                    }

                }
                else{

                    switch (tipo){

                        case "favorita":    fbFav.setImageResource(R.drawable.star);
                                            fbFav.setTag("1");
                                            break;

                        case "pendiente":   fbTime.setImageResource(R.drawable.clock);
                                            fbTime.setTag("1");
                                            break;

                        case "vista":       fbEye.setImageResource(R.drawable.eye);
                                            fbEye.setTag("1");
                                            break;
                    }

                }



            }
        });

        request.add(jsonObjectRequest);

    }

    //Llamada a WS para recuperar las preferencias del usuario
    public void recuperarPreferenciasUsuario(){

        String url = getString(R.string.url);
        url = url + "/Preferencias_user.php";

        parametros = null;
        parametros = new JSONObject();
        try {
            parametros.put("id_user", user_id);
            parametros.put("id_pelicula", id_pelicula);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("preferencias");

                    JSONObject json = jsonArray.getJSONObject(0);

                    if(!json.optBoolean("error")){

                        if(json.optBoolean("favorita")){

                            fbFav.setImageResource(R.drawable.star);
                            fbFav.setTag("1");

                        }
                        else{

                            fbFav.setImageResource(R.drawable.favorite);
                            fbFav.setTag("0");
                        }


                        if(json.optBoolean("pendiente")){

                            fbTime.setImageResource(R.drawable.checked);
                            fbTime.setTag("1");

                        }
                        else{

                            fbTime.setImageResource(R.drawable.clock);
                            fbTime.setTag("0");

                        }

                        if(json.optBoolean("vista")){

                            fbEye.setImageResource(R.drawable.eye_blocked);
                            fbEye.setTag("1");

                        }
                        else{

                            fbEye.setImageResource(R.drawable.eye);
                            fbEye.setTag("0");

                        }


                    }
                    else{

                        Toast.makeText(getContext(),"No se pudieron recuperar sus preferencias",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Se ha producido un error. \nNo se pudieron recuperar sus preferencias",Toast.LENGTH_SHORT).show();


            }
        });

        request.add(jsonObjectRequest);


    }

}
