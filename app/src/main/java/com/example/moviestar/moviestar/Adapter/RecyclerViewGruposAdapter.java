package com.example.moviestar.moviestar.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.moviestar.moviestar.Entidades.Grupo;
import com.example.moviestar.moviestar.Fragments.FAmigos;
import com.example.moviestar.moviestar.Grupos;
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewGruposAdapter extends RecyclerView.Adapter<RecyclerViewGruposAdapter.VHolder>{

    private Context context;
    private  ArrayList<Grupo> grupos;
    private ArrayList<Amigo> amigos;
    private ArrayList<Amigo> amigosAñadidos;
    private int user_id;
    TextView amigosAñadidosText;
    private String identificador;
    private int contador;
    private TextView numeroParticipantes;

    public RecyclerViewGruposAdapter(Context context, ArrayList<Grupo> grupos, int user_id, String identificador){
        this.context = context;
        this.grupos = grupos;
        this.user_id = user_id;
        this.identificador = identificador;

    }

    public RecyclerViewGruposAdapter(Context context, ArrayList<Amigo> amigos, int user_id, String identificador, TextView amigosAñadidos, TextView numeroParticipantes){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
        this.identificador = identificador;
        this.amigosAñadidos = new ArrayList<>();
        this.amigosAñadidosText = amigosAñadidos;
        this.contador = 1;
        this.numeroParticipantes = numeroParticipantes;
    }




    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista;

        if(identificador.equals("grupos"))
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.gruposview,parent,false);
        else {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.gruposamigosview, parent, false);

            if (vista.getLayoutParams ().width == RecyclerView.LayoutParams.MATCH_PARENT)
                vista.getLayoutParams ().width = parent.getWidth ();

        }


        return new VHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, int position) {

        if(identificador.equals("grupos")){
            final Grupo grupo = grupos.get(position);

            if(grupo.getUrlImagen() != null){

                Picasso.get().load(grupo.getUrlImagen()).noFade().placeholder(context.getResources().getDrawable(R.drawable.cinefondo)).error(context.getResources().getDrawable(R.drawable.cinefondo)).resize(500,600).into(holder.imagen);

            }
            else{

                holder.imagen.setImageResource(R.drawable.cinefondo);

            }

            holder.titulo.setText(grupo.getNombre_grupo());
            holder.fecha.setText(grupo.getFecha());
            holder.participantes.setText(String.valueOf(grupo.getNumero_participantes())+"/10");

            holder.grupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id_grupo_creado = grupo.getId_grupo();

                    Intent intent = new Intent(context, Grupos.class);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("id_grupo",id_grupo_creado);
                    intent.putExtra("identificador","fgrupos");

                    context.startActivity(intent);
                }
            });





        }
        else{

            final Amigo amigo = amigos.get(position);

            holder.amigo.setText(amigo.getNombreUsuario());
            holder.pais.setText(amigo.getPais());

            Boolean flagButton = false;

            holder.button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    String textoAccion = holder.accion.getText().toString();

                    if(textoAccion.equals("Añadir")){

                        if(contador < 10){
                            holder.accion.setText("Quitar");
                            holder.button.setCardBackgroundColor(Color.parseColor("#FF0000"));
                            amigosAñadidos.add(amigo);
                            contador++;

                            String texto = amigosAñadidosText.getText().toString();

                            if(texto.equals(""))
                                amigosAñadidosText.setText(amigo.getNombreUsuario());
                            else
                                amigosAñadidosText.setText(texto + ", " + amigo.getNombreUsuario());
                        }
                        else{
                            Toast.makeText(context,"Solo puede haber como máximo 10 participantes",Toast.LENGTH_SHORT).show();
                        }




                    }
                    else {

                        holder.accion.setText("Añadir");
                        holder.button.setCardBackgroundColor(Color.parseColor("#68D853"));
                        amigosAñadidos.remove(amigo);
                        contador--;
                        String texto = "";

                        for(int i = 0; i < amigosAñadidos.size(); i++){

                                Amigo amigo = amigosAñadidos.get(i);
                                if(i == amigosAñadidos.size() -1){
                                    texto = texto + amigo.getNombreUsuario();
                                }
                                else{
                                    texto = texto + amigo.getNombreUsuario()+",";
                                }


                        }
                        amigosAñadidosText.setText(texto);



                    }



                    numeroParticipantes.setText(contador+"/10");


                }
            });

        }

    }

    @Override
    public int getItemCount() {

        if(identificador.equals("grupos")){

            return grupos.size();
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
