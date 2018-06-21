package com.example.moviestar.moviestar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.moviestar.moviestar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapterAmigos extends RecyclerView.Adapter<RecyclerViewAdapterAmigos.VHolder>{

    private Context context;
    private Activity Activity;
    private ArrayList<Amigo> amigos;
    private ArrayList<Amigo> amigosAux;
    private String id;
    private int user_id;
    private RequestQueue request;
    private String identificador;
    private  EditText search;
    private String user_name;

    public RecyclerViewAdapterAmigos(Context context, ArrayList<Amigo> amigos, int user_id, String identificador,Activity Activity){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
        this.identificador = identificador;
        this.Activity = Activity;
        request = Volley.newRequestQueue(context);

    }

    public RecyclerViewAdapterAmigos(Context context, ArrayList<Amigo> amigos, int user_id, String identificador,Activity Activity,String user_name){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
        this.identificador = identificador;
        this.Activity = Activity;
        request = Volley.newRequestQueue(context);
        this.user_name= user_name;

    }

    public RecyclerViewAdapterAmigos(Context context, ArrayList<Amigo> amigos, int user_id, String identificador, Activity Activity, EditText search){
        this.context = context;
        this.amigos = amigos;
        this.user_id = user_id;
        this.identificador = identificador;
        this.Activity = Activity;
        request = Volley.newRequestQueue(context);
        this.amigosAux = this.amigos;
        this.search = search;
    }



    @NonNull
    @Override
    public RecyclerViewAdapterAmigos.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista;

        if(identificador.equals("peticiones"))
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.peticiones_amistad_view,parent,false);
        else
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.amigosview,parent,false);


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

            holder.imagenPerfil.setImageResource(R.drawable.usuario);

        }


        if(identificador.equals("amigos")){

            holder.añadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Confirmación")
                            .setMessage("Esta seguro/a?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    gestionarPeticiones(amigo,"borrar");
                                    amigos.remove(amigo);
                                    notifyItemRemoved(position);

                                }})
                            .setNegativeButton(android.R.string.no, null).show();


                }
            });

        }else if(identificador.equals("listaUsuarios")){

            holder.textoButton1.setText("Enviar peticion");

            holder.añadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Confirmación")
                            .setMessage("¿Enviar petición de amistad?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    gestionarPeticiones(amigo,"peticion");

                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                }
            });



        }else if (identificador.equals("peticiones")){


            holder.aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Confirmación")
                            .setMessage("Esta seguro/a?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    gestionarPeticiones(amigo,"aceptado");

                                    amigos.remove(amigo);
                                    notifyItemRemoved(position);



                                    if(amigos.size() == 0){

                                        Intent intent = new Intent(context,AreaAmigos.class);

                                        intent.putExtra("user_id",user_id);
                                        intent.putExtra("user_name",user_name);

                                        context.startActivity(intent);

                                        Activity.overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out);


                                    }

                                }})
                            .setNegativeButton(android.R.string.no, null).show();



                }
            });


            holder.rechazar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Confirmación")
                            .setMessage("Esta seguro/a?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    gestionarPeticiones(amigo,"rechazado");
                                    amigos.remove(amigo);
                                    notifyItemRemoved(position);

                                    if(amigos.size() == 0){

                                        Intent intent = new Intent(context,AreaAmigos.class);


                                        intent.putExtra("user_id",user_id);

                                        context.startActivity(intent);

                                        Activity.overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out);




                                    }

                                }})
                            .setNegativeButton(android.R.string.no, null).show();


                }
            });

        }




    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }




    class VHolder extends RecyclerView.ViewHolder {

        ImageView imagenPerfil;
        TextView nombreAmigo, paisAmigo,textoButton1;
        CardView añadir,aceptar,rechazar;
        View separator;


        public VHolder(View viewLayout) {
            super(viewLayout);


            if(identificador.equals("peticiones")) {

                imagenPerfil = itemView.findViewById(R.id.imagenPerfil);
                nombreAmigo = itemView.findViewById(R.id.tvAmigo);
                paisAmigo = itemView.findViewById(R.id.tvPais);
                aceptar = itemView.findViewById(R.id.buttonAccion);
                rechazar = itemView.findViewById(R.id.buttonRechazar);
                separator = itemView.findViewById(R.id.separator);
            }
            else{

                imagenPerfil = itemView.findViewById(R.id.imagenPerfil);
                nombreAmigo = itemView.findViewById(R.id.tvAmigo);
                paisAmigo = itemView.findViewById(R.id.tvPais);
                añadir = itemView.findViewById(R.id.buttonAmigos);
                textoButton1 = itemView.findViewById(R.id.tvTextoAmigos);
                separator = itemView.findViewById(R.id.separator);
            }


        }

    }

    private void gestionarPeticiones(final Amigo amigo , String operacion){

        int id = amigo.getId();

        String url = context.getString(R.string.url);

        url = url + "/PeticionesAmistad.php";

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user",user_id);
            parametros.put("id_user_peticion",id);
            parametros.put("operacion",operacion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(identificador.equals("listaUsuarios")){

                    Toast.makeText(context,"Se ha enviado una peticion de amistad a "+ amigo.getNombreUsuario(),Toast.LENGTH_SHORT).show();
                    amigosAux.remove(amigo);
                    filtrarAmigos(amigosAux);
                    search.setText("");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Se ha producido un error",Toast.LENGTH_SHORT).show();

            }
        });

        request.add(jsonObjectRequest);



    }


    public void filtrarAmigos(ArrayList<Amigo> filtro){

        amigos = filtro;
        notifyDataSetChanged();

    }

}
