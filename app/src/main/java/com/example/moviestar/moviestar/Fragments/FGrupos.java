package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.moviestar.moviestar.Adapter.RecyclerViewGruposAdapter;
import com.example.moviestar.moviestar.CrearGrupo;
import com.example.moviestar.moviestar.Entidades.Grupo;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FGrupos extends Fragment {

    FloatingActionButton flabAdd;
    View vista;
    int user_id;
    RequestQueue request;
    ArrayList<Grupo> grupos;

    RecyclerView recyclerAmigosGrupos;
    LinearLayoutManager manager;
    RecyclerViewGruposAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_grupos, container, false);

        user_id = getArguments().getInt("id");

        request = Volley.newRequestQueue(getContext());


        flabAdd = vista.findViewById(R.id.flabAdd);
        flabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crearGrupo = new Intent(getContext(), CrearGrupo.class);
                crearGrupo.putExtra("user_id",user_id);

                startActivity(crearGrupo);


            }
        });

        grupos = new ArrayList<>();
        recyclerAmigosGrupos = vista.findViewById(R.id.recyclerGrupo);

        manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerAmigosGrupos.setHasFixedSize(true);
        recyclerAmigosGrupos.setLayoutManager(manager);

        adapter = new RecyclerViewGruposAdapter(getContext(),grupos,user_id,"grupos");
        recyclerAmigosGrupos.setAdapter(adapter);


        return vista;


    }


    @Override
    public void onResume() {
        super.onResume();

        recuperarGrupos();


    }

    //Llamada a WS para recuperar los grupos de los que forma parte el usuario
    private void recuperarGrupos() {

        grupos.clear();
        String url = getString(R.string.url);

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "/recuperarGrupos.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonObject;

                try {
                    JSONArray jsonArray = response.getJSONArray("grupos");

                    for(int i = 0; i<jsonArray.length(); i++){

                        jsonObject = jsonArray.getJSONObject(i);

                        Grupo grupo = new Grupo();

                        grupo.setId_grupo(Integer.parseInt(jsonObject.optString("ID_GRUPO")));
                        grupo.setFecha(jsonObject.optString("FECHA"));
                        grupo.setNombre_grupo(jsonObject.optString("NOMBRE"));
                        grupo.setNumero_participantes(Integer.parseInt(jsonObject.optString("NUMERO_PARTICIPANTES")));


                        grupos.add(grupo);
                    }

                        adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });

        request.add(jsonObjectRequest);


    }
}
