package com.example.moviestar.moviestar.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.AreaAmigos;
import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.Fragments.FAmigos;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewGruposAdapter extends RecyclerView.Adapter<RecyclerViewGruposAdapter.VHolder>{

    private Context context;
    //private  ArrayList<Grupo> grupos;
    private ArrayList<Amigo> amigos;
    private ArrayList<Amigo> amigosAñadidos;
    private int user_id;
    TextView amigosAñadidosText;
    private String identificador;
    boolean flagButton;

    /*public RecyclerViewGruposAdapter(Context context, ArrayList<Grupo> grupos, int user_id, String identificador,){
        this.context = context;
        this.grupos = grupos;
        this.user_id = user_id;
        this.identificador = identificador;

    }*/

    public RecyclerViewGruposAdapter(Context context, ArrayList<Amigo> amigos, int user_id, String identificador, TextView amigosAñadidos){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
        this.identificador = identificador;
        this.amigosAñadidos = new ArrayList<>();
        this.amigosAñadidosText = amigosAñadidos;
        this.flagButton = false;
    }




    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista;

        if(identificador.equals("grupos"))
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.gruposview,parent,false);
        else
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.gruposamigosview,parent,false);


        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, int position) {

        if(identificador.equals("grupos")){




        }
        else{

            final Amigo amigo = amigos.get(position);

            holder.amigo.setText(amigo.getNombreUsuario());
            holder.pais.setText(amigo.getPais());


            holder.button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    if(!flagButton){

                        holder.accion.setText("Quitar");
                        holder.button.setBackgroundColor(R.color.red);
                        amigosAñadidos.add(amigo);
                        String texto = amigosAñadidosText.getText().toString();
                        amigosAñadidosText.setText(texto + ", " + amigo.getNombreUsuario());


                    }
                    else {

                        holder.accion.setText("Añadir");
                        holder.button.setBackgroundColor(R.color.green);
                        amigosAñadidos.remove(amigo);

                        for(int i = 0; i < amigosAñadidos.size(); i++){

                            if(i == amigosAñadidos.size() -1 ){
                                String texto = amigo.getNombreUsuario();
                            }
                            else{
                                String texto = amigo.getNombreUsuario()+",";
                            }


                        }

                    }






                }
            });

        }

    }

    @Override
    public int getItemCount() {

        if(identificador.equals("grupos")){

            return amigos.size(); //CAMBIAR ESTO A GRUPOS.SIZE() --------------------------------------
        }
        else{
            return amigos.size();
        }
    }




    class VHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView titulo, fecha,participantes,amigo,pais,accion;
        CardView button,grupo;

        public VHolder(View viewLayout) {
            super(viewLayout);


            if(identificador.equals("grupos")) {

               imagen = itemView.findViewById(R.id.imagenGrupo);
               titulo = itemView.findViewById(R.id.tvTituloGrupo);
               fecha = itemView.findViewById(R.id.tvFechaGrupo);
               participantes = itemView.findViewById(R.id.tvnumero);
               grupo = itemView.findViewById(R.id.grupo);


            }
            else{

                imagen = itemView.findViewById(R.id.imagenPerfil);
                amigo = itemView.findViewById(R.id.tvAmigo);
                pais = itemView.findViewById(R.id.tvPais);
                accion = itemView.findViewById(R.id.tvAccion);
                button = itemView.findViewById(R.id.buttonAccion);

            }


        }

    }


    public ArrayList<Amigo> getAmigosAñadidos(){

        return amigosAñadidos;
    }



}
