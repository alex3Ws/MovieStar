package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FPeliculas extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    View vista;
    int cont = 1;
    RecyclerView recycledPeliculas;
    ArrayList<Pelicula> listaPeliculas;
    ArrayList<String> listaGeneros;
    Boolean isScrolling = false;
    RecyclerViewAdapter adapter;
    int currentItems, totalItems,scrollOutItems;
    GridLayoutManager manager;
    ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista=inflater.inflate(R.layout.fragment_peliculas, container, false);

        listaPeliculas = new ArrayList<>();
        listaGeneros = new ArrayList<>();

        request =  Volley.newRequestQueue(getContext());

        recycledPeliculas = vista.findViewById(R.id.recycledView);

        manager = new GridLayoutManager(getContext(), 2);
        recycledPeliculas.setHasFixedSize(true);
        recycledPeliculas.setLayoutManager(manager);

        adapter = new RecyclerViewAdapter(getContext(), listaPeliculas);
        recycledPeliculas.setAdapter(adapter);



        adapter.notifyDataSetChanged();

        progressBar = vista.findViewById(R.id.progreso);

        progressBar.setVisibility(View.VISIBLE);

        llamarApi();


        return vista;
    }

    public void llamarApi(){


        String url  = "https://api.themoviedb.org/3/discover/movie?api_key=a2424ed363ead46acaa726cf8cb45bad&language=en-US&" +
                "sort_by=popularity.desc&include_adult=false&include_video=false&page="+cont;


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        cont++;
    }


    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = null;
        json = response.optJSONArray("results");


        try {

            for(int i=0;i<json.length();i++){
                Bitmap bitmap;
                final Pelicula p = new Pelicula();

                JSONObject jsonObject = null;

                jsonObject = json.getJSONObject(i);

                p.setId(jsonObject.optInt("id"));
                p.setValoracion(jsonObject.optDouble("vote_average"));
                p.setTitulo(jsonObject.optString("title"));
                p.setCaratula(jsonObject.optString("poster_path"));
                p.setSinopsis(jsonObject.optString("overview"));
                p.setAno(jsonObject.optString("release_date"));

                String imagen = p.getCaratula();
                ImageRequest imageRequest = new ImageRequest(imagen, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        p.setImagen(scaleDown(response, true));


                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {

                    public void onErrorResponse (VolleyError error){

                        Toast.makeText(getContext(),"Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    }
                });

                request.add(imageRequest);

                JSONArray json2 = null;
                json2 = jsonObject.optJSONArray("genre_ids");

                for(int j=0;j<json2.length();j++){

                    int genero_id = json2.getInt(j);

                    listaGeneros = obtenerGeneros(genero_id);

                }
                p.setGeneros(listaGeneros);

                listaPeliculas.add(p);
            }

            adapter.notifyDataSetChanged();



        } catch (JSONException e) {
            e.printStackTrace();
        }




        /*recycledPeliculas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){

                    isScrolling = true;

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)){

                    isScrolling = false;
                    llamarApi();

                }
            }
        });*/

        progressBar.setVisibility(View.GONE);

    }
    
    public static Bitmap scaleDown(Bitmap realImage,
                                   boolean filter) {
        int width = 320;
        int height = 270;

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),"Error", Toast.LENGTH_LONG).show();

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
}
