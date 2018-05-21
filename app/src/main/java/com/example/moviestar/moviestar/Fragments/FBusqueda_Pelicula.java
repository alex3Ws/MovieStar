package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FBusqueda_Pelicula extends Fragment {

    View vista;
    RequestQueue request;
    int user_id;
    String query;
    RecyclerView recyclerView;
    ArrayList<Pelicula> listaPeliculas;
    ArrayList<String> listaGeneros;
    RecyclerViewAdapter adapter;
    //GridLayoutManager manager;
    LinearLayoutManager manager2;
    TextView texto;
    String procedencia_llamada = "FBusqueda_Pelicula";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_fbusqueda_pelicula, container, false);

        request = Volley.newRequestQueue(getContext());

        user_id = getArguments().getInt("user_id",0);
        query = getArguments().getString("query");

        listaPeliculas = new ArrayList<>();
        listaGeneros = new ArrayList<>();

        recyclerView = vista.findViewById(R.id.recyclerBusqueda);
        texto = vista.findViewById(R.id.tvbusqueda);
        texto.setText(texto.getText().toString() + query);

        //manager = new GridLayoutManager(getContext(), 2);
        manager2 = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager2);

        adapter = new RecyclerViewAdapter(getContext(), listaPeliculas, user_id,procedencia_llamada);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        query = query.replace(" ", "%20");

        llamarApi();

        return vista;
    }

    private void llamarApi() {

        String url = "https://api.themoviedb.org/3/search/movie?api_key=a2424ed363ead46acaa726cf8cb45bad&query="+query+"&language=es-ES";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                JSONArray json = null;
                json = response.optJSONArray("results");

                try {

                    for(int i=0;i<json.length();i++){


                        JSONObject jsonObject = null;

                        jsonObject = json.getJSONObject(i);

                        if(jsonObject.optString("poster_path") != "null") {

                            final Pelicula p = new Pelicula();

                            p.setId(jsonObject.optInt("id"));
                            p.setValoracion(jsonObject.optDouble("vote_average"));
                            p.setTitulo(jsonObject.optString("title"));
                            p.setTitulo_original(jsonObject.optString("original_title"));
                            p.setCaratula(jsonObject.optString("poster_path"));
                            p.setSinopsis(jsonObject.optString("overview"));
                            p.setAno(jsonObject.optString("release_date"));

                            JSONArray json2 = null;
                            json2 = jsonObject.optJSONArray("genre_ids");

                            for (int j = 0; j < json2.length(); j++) {

                                int genero_id = json2.getInt(j);

                                listaGeneros = obtenerGeneros(genero_id);

                            }

                            p.setGeneros(listaGeneros);

                            listaPeliculas.add(p);
                        }


                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error Busqueda",Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonObjectRequest);



    }

    public ArrayList<String> obtenerGeneros(int genero_id) {
        String genero = null;

        ArrayList<String> generos = new ArrayList<>();

        switch (genero_id){
            case 28 :       genero = "Acción";
                generos.add(genero);
                break;
            case 12 :       genero = "Aventura";
                generos.add(genero);
                break;
            case 16 :       genero = "Animación";
                generos.add(genero);
                break;
            case 35 :       genero = "Comedia";
                generos.add(genero);
                break;
            case 80 :       genero = "Crimen";
                generos.add(genero);
                break;
            case 99 :       genero = "Documental";
                generos.add(genero);
                break;
            case 18 :       genero = "Drama";
                generos.add(genero);
                break;
            case 10751 :    genero = "Familia";
                generos.add(genero);
                break;
            case 14 :       genero = "Fantasía";
                generos.add(genero);
                break;
            case 36 :       genero = "Historia";
                generos.add(genero);
                break;
            case 27 :       genero = "Terror";
                generos.add(genero);
                break;
            case 10402 :    genero = "Música";
                generos.add(genero);
                break;
            case 9648 :     genero = "Misterio";
                generos.add(genero);
                break;
            case 10749 :    genero = "Romance";
                generos.add(genero);
                break;
            case 878 :      genero = "Ciencia Ficción";
                generos.add(genero);
                break;
            case 53 :       genero = "Suspense";
                generos.add(genero);
                break;
            case 10752 :    genero = "Guerra";
                generos.add(genero);
                break;
            case 37 :       genero = "Western";
                generos.add(genero);
                break;
            default:        genero = "Desconocido";
                generos.add(genero);
        }

        return generos;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter.notifyDataSetChanged();

    }

}
