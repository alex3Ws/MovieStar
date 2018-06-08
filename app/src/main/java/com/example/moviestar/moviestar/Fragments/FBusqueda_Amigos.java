package com.example.moviestar.moviestar.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterAmigos;
import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FBusqueda_Amigos extends Fragment {

    View vista;
    int user_id;
    RequestQueue request;
    RecyclerView recyclerView;
    RecyclerViewAdapterAmigos adapterAmigos;
    LinearLayoutManager manager;
    ArrayList<Amigo> amigos;
    EditText busqueda;
    Boolean flag = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_busqueda_amigos, container, false);

        user_id = getArguments().getInt("user_id");
        amigos = new ArrayList<>();
        busqueda = vista.findViewById(R.id.busquedaAmigos);
        recyclerView = vista.findViewById(R.id.recyclerListaUsuarios);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapterAmigos = new RecyclerViewAdapterAmigos(getContext(),amigos,user_id,"listaUsuarios",getActivity(),busqueda);
        recyclerView.setAdapter(adapterAmigos);

        request = Volley.newRequestQueue(getContext());



        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                    filtrar(s.toString());


            }
        });





        consultarAmigos();



        return vista;
    }

    private void filtrar(String busqueda){

        ArrayList<Amigo> filtro = new ArrayList<>();

        for(Amigo amigo : amigos){

            if(amigo.getNombreUsuario().toLowerCase().contains(busqueda.toLowerCase())){

                filtro.add(amigo);

            }

        }
        //flag = false;
        adapterAmigos.filtrarAmigos(filtro);

    }

    private void consultarAmigos() {

        amigos.clear();

        String url = getContext().getString(R.string.url);

        url = url + "/consultarListaUsuarios.php";

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user",user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("usuarios");

                for(int i = 0; i < jsonArray.length(); i++){

                    Amigo amigo = new Amigo();
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        amigo.setId(Integer.parseInt(jsonObject.optString("id_user")));
                        amigo.setNombreUsuario(jsonObject.optString("nombre"));

                        amigos.add(amigo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                    adapterAmigos.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jsonObjectRequest);




    }

}
