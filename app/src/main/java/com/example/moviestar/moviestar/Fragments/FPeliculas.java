package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FPeliculas extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView t1,t2;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button b1;
    View vista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_peliculas, container, false);

        t1 = (TextView) vista.findViewById(R.id.tt1);
        t2 = (TextView) vista.findViewById(R.id.tt2);

        request = (RequestQueue) Volley.newRequestQueue(getContext());

        b1 = (Button) vista.findViewById(R.id.buttonarg);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirWS();
            }
        });


        return vista;
    }

    private void consumirWS() {



        String url  = "https://api.themoviedb.org/3/movie/29845?api_key=a2424ed363ead46acaa726cf8cb45bad&language=en-US";

        //String url = "http://192.168.140.1/WebServices_PHP/RegistrarUsuario.php?nombre="+nombre.getText().toString()+
        //      "&email="+email.getText().toString()+"&contrasena="+contrasena.getText().toString();


        Toast.makeText(getContext(),"FEGREG",Toast.LENGTH_SHORT).show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        request.add(jsonObjectRequest);
        Toast.makeText(getContext(),"dddddd",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {

        try {

            Toast.makeText(getContext(),"hhhhh",Toast.LENGTH_SHORT).show();

            t1.setText(response.getString("original_title"));
            t2.setText(response.getString("original_language"));

            Toast.makeText(getContext(),"jjj",Toast.LENGTH_SHORT).show();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Errorrrr", Toast.LENGTH_LONG).show();
    }

}
