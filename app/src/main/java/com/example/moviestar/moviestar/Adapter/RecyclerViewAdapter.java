package com.example.moviestar.moviestar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.InfoPeliculas;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VHolder> implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private ArrayList<Pelicula> peliculas;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    JSONObject jsonObject;
    Intent intent;
    String id;
    int user_id;

    public RecyclerViewAdapter(Context context,ArrayList<Pelicula> peliculas,int  user_id){
        this.context = context;
        this.peliculas = peliculas;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.peliculasview,parent,false);

        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.VHolder holder, int position) {

        id = null;

        final Pelicula pelicula = peliculas.get(position);

        holder.titulo.setText(pelicula.getTitulo());
        holder.valoracion.setText(String.valueOf(pelicula.getValoracion()));


        if(pelicula.getCaratula() != null){

            Picasso.get().load(pelicula.getCaratula()).placeholder(context.getResources().getDrawable(R.drawable.cinefondo)).error(context.getResources().getDrawable(R.drawable.cinefondo)).resize(500,600).into(holder.imageView);

        }
        else{

            holder.imageView.setImageResource(R.drawable.cinefondo);

        }



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = String.valueOf(pelicula.getId());
                llamarApi();


            }
        });

    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }


    private void llamarApi() {

        request = null;

        request = Volley.newRequestQueue(context);

        String url  = "https://api.themoviedb.org/3/movie/"+id+"?api_key=a2424ed363ead46acaa726cf8cb45bad&language=es-ES";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {


            jsonObject = response;

            intent = new Intent(context,InfoPeliculas.class);

            intent.putExtra("json",jsonObject.toString());
            intent.putExtra("id",id);
            intent.putExtra("user_id",user_id);

            context.startActivity(intent);


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();

    }




    class VHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titulo, valoracion;


        public VHolder(View viewLayout) {
            super(viewLayout);

            imageView = itemView.findViewById(R.id.caratula);
            titulo =  itemView.findViewById(R.id.titulo);
            valoracion = itemView.findViewById(R.id.valoracion);



        }

    }

}