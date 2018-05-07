package com.example.moviestar.moviestar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.InfoPeliculas;
import com.example.moviestar.moviestar.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VHolder>{

    private Context context;
    private int layout;
    private ArrayList<Pelicula> peliculas;
    RequestQueue request;

    public RecyclerViewAdapter(Context context, int layout, ArrayList<Pelicula> peliculas){
        this.context = context;
        this.layout = layout;
        this.peliculas = peliculas;
        this.request = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View viewLayout =  inflater.inflate(layout, parent, false);

        return new VHolder(viewLayout, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.VHolder holder, int position) {
        Pelicula item = peliculas.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    class VHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titulo, valoracion;
        Context context;



        public VHolder(View viewLayout, Context context) {
            super(viewLayout);

            this.context = context;

        }

        public void bind(final Pelicula pelicula){

            String imagen = pelicula.getCaratula();


            imageView = itemView.findViewById(R.id.caratula);

            ImageRequest imageRequest = new ImageRequest(imagen, new Response.Listener<Bitmap>() {
                @Override
                    public void onResponse(Bitmap response) {

                    Bitmap scaledBitmap = scaleDown(response, 300, true);

                    imageView.setImageBitmap(scaledBitmap);

                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {

                    public void onErrorResponse (VolleyError error){

                        Toast.makeText(context,"Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    }
            });

            request.add(imageRequest);

             titulo =  itemView.findViewById(R.id.titulo);
             valoracion = itemView.findViewById(R.id.valoracion);

             titulo.setText(pelicula.getTitulo());
             valoracion.setText(String.valueOf(pelicula.getValoracion()));

             imageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(context, InfoPeliculas.class);

                     intent.putExtra("Peli",pelicula);

                     context.startActivity(intent);
                 }
             });

        }

    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        int width = 320;
        int height = 270;

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

}
