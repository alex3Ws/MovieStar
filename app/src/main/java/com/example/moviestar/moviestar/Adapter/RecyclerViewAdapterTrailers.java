package com.example.moviestar.moviestar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterTrailers extends RecyclerView.Adapter<RecyclerViewAdapterTrailers.VHolder>{

    private Context context;
    private ArrayList<String> trailers;

    public RecyclerViewAdapterTrailers(Context context,ArrayList<String> trailers){
        this.context = context;
        this.trailers = trailers;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterTrailers.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailersview,parent,false);

        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTrailers.VHolder holder, int position) {

        String cadena = trailers.get(position);

        if(!cadena.equals("Trailer no disponible")){

            final String key = cadena.substring(0,11);
            String texto = cadena.substring(11);

            holder.texto.setText(texto);

            String url = "https://img.youtube.com/vi/"+key+"/maxresdefault.jpg";

            Picasso.get().load(url).placeholder(context.getResources().getDrawable(R.drawable.cinefondo)).error(context.getResources().getDrawable(R.drawable.cinefondo)).resize(1050,600).into(holder.imagen);

            holder.imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+key));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    context.startActivity(intent);
                }
            });

        }
        else{

            holder.texto.setText(cadena);
            holder.imagen.setImageResource(R.drawable.cinefondo);

        }




    }

    @Override
    public int getItemCount() { return trailers.size(); }

    class VHolder extends RecyclerView.ViewHolder {


        TextView texto;
        ImageView imagen;

        public VHolder(View itemView){
            super(itemView);

            texto = itemView.findViewById(R.id.tvidioma);
            imagen = itemView.findViewById(R.id.imagenVideo);

        }
    }
}