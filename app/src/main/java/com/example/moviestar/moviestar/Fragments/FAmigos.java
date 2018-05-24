package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterAmigos;
import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class FAmigos extends Fragment {

    View vista;
    RecyclerView recyclerAmigos;
    LinearLayoutManager manager;
    RecyclerViewAdapterAmigos adapter;
    ArrayList<Amigo> amigos;
    ArrayList<String> ids_user_amigos;
    RequestQueue request;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_amigos, container, false);

        recyclerAmigos = vista.findViewById(R.id.recyclerAmigos);

        manager = new LinearLayoutManager(getContext());
        recyclerAmigos.setHasFixedSize(true);
        recyclerAmigos.setLayoutManager(manager);

        amigos = new ArrayList<>();
        ids_user_amigos = new ArrayList<>();

        adapter = new RecyclerViewAdapterAmigos(getContext(),amigos,3);
        recyclerAmigos.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        request = Volley.newRequestQueue(getContext());

        consultarAmigos();


        return vista;
    }

    private void consultarAmigos() {

        String url = getString(R.string.url);
        url = url + "/ConsultarAmigos.php?id_user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String id;

                try {
                    JSONArray jsonArray = response.getJSONArray("amigos");

                    for(int i = 0; i<jsonArray.length(); i++){
                        


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }


}
