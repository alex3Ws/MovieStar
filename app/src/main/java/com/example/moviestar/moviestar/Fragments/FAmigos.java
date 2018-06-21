package com.example.moviestar.moviestar.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    RequestQueue request;
    NavigationView navigationView;
    int user_id;
    Activity Activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_amigos, container, false);

        Activity = getActivity();

        user_id = getArguments().getInt("user_id");

        recyclerAmigos = vista.findViewById(R.id.recyclerAmigos);

        manager = new LinearLayoutManager(getContext());
        recyclerAmigos.setHasFixedSize(true);
        recyclerAmigos.setLayoutManager(manager);

        amigos = new ArrayList<>();

        navigationView = getActivity().findViewById(R.id.nav_view);

        request = Volley.newRequestQueue(getContext());

        adapter = new RecyclerViewAdapterAmigos(getContext(),amigos,user_id,"amigos",Activity);
        recyclerAmigos.setAdapter(adapter);

        consultarAmigos();

        return vista;
    }

    //Llamada a WS para recuperar los amigos del usuario
    private void consultarAmigos() {

        String url = getString(R.string.url);

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user", user_id);
            parametros.put("aceptado",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "/ConsultarAmigos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if(response.optString("error") == "WS no retorna"){

                        Toast.makeText(getContext(),"No se pudo recuperar la lista de amigos",Toast.LENGTH_SHORT).show();

                    }
                    else if (response.has("amigos")){

                        JSONArray jsonArray = response.getJSONArray("amigos");


                        for(int i = 0; i<jsonArray.length(); i++){

                            JSONObject jsonObject = null;
                            jsonObject = jsonArray.optJSONObject(i);

                            Amigo amigo = new Amigo();

                            amigo.setId(Integer.parseInt(jsonObject.optString("id_user_amigo")));
                            amigo.setNombreUsuario(jsonObject.optString("nombre"));

                            amigos.add(amigo);

                        }
                        adapter.notifyDataSetChanged();


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Se ha producido un error al cargar la lista de amigos",Toast.LENGTH_SHORT).show();

            }
        });

        request.add(jsonObjectRequest);


    }


}

