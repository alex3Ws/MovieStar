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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.InfoPeliculas;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VHolder>{

    private Context context;
    private ArrayList<Pelicula> peliculas;

    public RecyclerViewAdapter(Context context,ArrayList<Pelicula> peliculas){
        this.context = context;
        this.peliculas = peliculas;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.peliculasview,parent,false);

        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.VHolder holder, int position) {

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
                Intent intent = new Intent(context, InfoPeliculas.class);

                intent.putExtra("id",String.valueOf(pelicula.getId()));

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return peliculas.size();
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
