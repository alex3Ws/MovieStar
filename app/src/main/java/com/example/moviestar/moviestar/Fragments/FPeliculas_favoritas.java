package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterPreferenciasUsuario;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.InfoPeliculas;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class FPeliculas_favoritas extends Fragment {

    View vista;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    RecyclerView recyclerViewFavoritas;
    ArrayList<Integer> id_pelicula;
    ArrayList<Pelicula> peliculas;
    ArrayList<JSONObject> responses;
    RecyclerViewAdapterPreferenciasUsuario adapter;
    GridLayoutManager manager;
    ProgressBar progressBar;
    String preferencia = "favoritas";
    JSONObject jsonObject;
    int user_id;
    boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_peliculas_favoritas, container, false);

        user_id = getArguments().getInt("user_id",0);

        recyclerViewFavoritas = vista.findViewById(R.id.recycledViewFavoritas);

        manager = new GridLayoutManager(getContext(), 2);
        recyclerViewFavoritas.setHasFixedSize(true);
        recyclerViewFavoritas.setLayoutManager(manager);

        request = Volley.newRequestQueue(getContext());

        id_pelicula = new ArrayList<>();
        peliculas = new ArrayList<>();
        responses = new ArrayList<>();




        llamarApi();





        return vista;
    }

    private void llamarApi() {


        String url = getString(R.string.url);
        url = url + "/Preferencias_user_all.php";

        JSONObject json = new JSONObject();

        try {
            json.put("id_user",user_id);
            json.put("preferencia",preferencia);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = null;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.has("peliculas")){

                    JSONObject jsonObject;
                    String id;
                    int id_peli;

                    try {
                        JSONArray jsonArray = response.getJSONArray("peliculas");

                        for(int i = 0; i<jsonArray.length(); i++){

                            jsonObject = jsonArray.getJSONObject(i);

                            id = jsonObject.optString("id");
                            id_peli = Integer.parseInt(id);

                            id_pelicula.add(id_peli);
                        }

                        llamarPeliculas(id_pelicula);;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    Toast.makeText(getContext(), response.optString("error"),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jsonObjectRequest);



    }

    private void llamarPeliculas(ArrayList ids) {

        int id;

        for(int i = 0; i<ids.size(); i++){

            id = (int) ids.get(i);


            String url  = "https://api.themoviedb.org/3/movie/"+id+"?api_key=a2424ed363ead46acaa726cf8cb45bad&language=es-ES";

            jsonObjectRequest = null;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    jsonObject = null;
                    jsonObject = response;

                    Pelicula p = new Pelicula();

                    p.setId(response.optInt("id"));
                    p.setCaratula(response.optString("poster_path"));
                    p.setTitulo(response.optString("title"));
                    p.setValoracion(response.optDouble("vote_average"));

                    peliculas.add(p);
                    responses.add(jsonObject);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(),"Error FPeliculas_favorita",Toast.LENGTH_SHORT).show();

                }
            });

            request.add(jsonObjectRequest);

        }

        adapter = new RecyclerViewAdapterPreferenciasUsuario(getContext(),peliculas,user_id,responses);
        recyclerViewFavoritas.setAdapter(adapter);
    }

}
