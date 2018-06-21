package com.example.moviestar.moviestar.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterTrailers;
import com.example.moviestar.moviestar.R;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FTrailerPeli extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    View vista;
    RecyclerView recyclerViewTrailer;
    ArrayList<String> trailers;
    RecyclerViewAdapterTrailers adapter;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    LinearLayoutManager manager;
    String id;
    FloatingActionButton fbFav,fbTime,fbEye;

    public static final String API_KEY = "AIzaSyBfEKxHbR4FZSZ96HZDzf7pVP1tABkmDI0";
    public static final String VIDEO_ID = "ImE-C23eba8";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista =  inflater.inflate(R.layout.fragment_trailer_peli, container, false);

        recyclerViewTrailer = vista.findViewById(R.id.recyclerViewTrailers);


        request = Volley.newRequestQueue(getContext());

        manager = new LinearLayoutManager(getContext());
        recyclerViewTrailer.setHasFixedSize(true);
        recyclerViewTrailer.setLayoutManager(manager);


        trailers = new ArrayList<>();

        id = getArguments().getString("id");


        llamarApi();



        return vista;
    }


    //Llamadas a la Api para obtener la url del trailer de la pelicula tanto en español como en ingles, y en ingles subtitulado al español
    private void llamarApi() {

        String url  = "http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=a2424ed363ead46acaa726cf8cb45bad&language=es-ES";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

        url = null;
        jsonObjectRequest = null;

        url  = "http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=a2424ed363ead46acaa726cf8cb45bad&language=en-US";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = null;
        json = response.optJSONArray("results");
        String cadena;


        try{

            for (int i = 0; i< json.length(); i++){

                cadena = null;
                JSONObject jsonObject = null;

                jsonObject = json.getJSONObject(i);

                if(jsonObject.has("site") && jsonObject.has("type")){

                    if(jsonObject.optString("site").equals("YouTube") && jsonObject.optString("type").equals("Trailer")){

                        String key = jsonObject.optString("key");
                        String texto = jsonObject.optString("name");

                        cadena = key + texto;

                        trailers.add(cadena);

                    }

                }
                else{
                    cadena = "Trailer no disponible";
                    trailers.add(cadena);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new RecyclerViewAdapterTrailers(getContext(), trailers);
        recyclerViewTrailer.setAdapter(adapter);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"Error al cargar los trailers",Toast.LENGTH_SHORT).show();

    }


}
