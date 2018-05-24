package com.example.moviestar.moviestar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.InfoPeliculas;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterAmigos extends RecyclerView.Adapter<RecyclerViewAdapterAmigos.VHolder>{

    private Context context;
    private ArrayList<Amigo> amigos;
    Intent intent;
    String id;
    int user_id;

    public RecyclerViewAdapterAmigos(Context context, ArrayList<Amigo> amigos, int user_id){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterAmigos.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.amigosview,parent,false);

        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAmigos.VHolder holder, final int position) {

        id = null;

        final Amigo amigo = amigos.get(position);

        if(position == (amigos.size() - 1)){
            holder.separator.setVisibility(View.GONE);
        }


        holder.nombreAmigo.setText(amigo.getNombreUsuario());
        holder.paisAmigo.setText(amigo.getPais());


        if(amigo.getUrlImagen() != null){

            Picasso.get().load(amigo.getUrlImagen()).noFade().placeholder(context.getResources().getDrawable(R.drawable.cinefondo)).error(context.getResources().getDrawable(R.drawable.cinefondo)).resize(500,600).into(holder.imagenPerfil);

        }
        else{

            holder.imagenPerfil.setImageResource(R.drawable.cinefondo);

        }


        holder.añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }




    class VHolder extends RecyclerView.ViewHolder {

        ImageView imagenPerfil;
        TextView nombreAmigo, paisAmigo;
        CardView añadir;
        View separator;


        public VHolder(View viewLayout) {
            super(viewLayout);

            imagenPerfil = itemView.findViewById(R.id.imagenPerfil);
            nombreAmigo =  itemView.findViewById(R.id.tvAmigo);
            paisAmigo = itemView.findViewById(R.id.tvPais);
            añadir = itemView.findViewById(R.id.buttonAmigos);
            separator = itemView.findViewById(R.id.separator);


        }

    }

}
