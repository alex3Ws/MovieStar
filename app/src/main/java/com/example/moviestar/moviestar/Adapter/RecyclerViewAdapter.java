package com.example.moviestar.moviestar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VHolder>{

    private Context context;
    private int layout;
    private ArrayList<Pelicula> peliculas;

    public RecyclerViewAdapter(Context context, int layout, ArrayList<Pelicula> peliculas){
        this.context = context;
        this.layout = layout;
        this.peliculas = peliculas;
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

        CardView cardView;
        ImageView imageView;
        TextView titulo, valoracion;


        public VHolder(View viewLayout, Context context) {
            super(viewLayout);

        }

        public void bind(Pelicula pelicula){

             titulo =  itemView.findViewById(R.id.titulo);
             titulo.setText(pelicula.getTitulo());
        }
    }

}
