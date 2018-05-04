package com.example.moviestar.moviestar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView registrarse;
    Button entrar;
    EditText usuario;
    EditText contrasena;
    ProgressBar progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Intent acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        entrar = findViewById(R.id.blogin);
        registrarse = findViewById(R.id.tvregister);
        usuario = findViewById(R.id.tbuser);
        contrasena = findViewById(R.id.tbcontraseña);
        progreso = findViewById(R.id.pbprogreso);

        request = Volley.newRequestQueue(getApplicationContext());

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"Se ha iniciado sesion correctamente",Toast.LENGTH_SHORT).show();
                consumirWS();

            }
        });


        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirSingUp = new Intent(getApplicationContext(),SingUpActivity.class);
                startActivity(abrirSingUp);
            }
        });
    }

    private void consumirWS() {


        progreso.setVisibility(View.VISIBLE);

        usuario.setFocusable(false);
        contrasena.setFocusable(false);

        String url = getString(R.string.url);

        url  = url +"/ConsultarUsuario.php?nombre="+usuario.getText().toString()+"&contrasena="+contrasena.getText().toString();

        //String url = "http://192.168.140.1/WebServices_PHP/RegistrarUsuario.php?nombre="+nombre.getText().toString()+
          //      "&email="+email.getText().toString()+"&contrasena="+contrasena.getText().toString();

        url = url.replace(" ","%20");


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        request.add(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {

        progreso.setVisibility(View.GONE);

        try {

            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;
            jsonObject = json.getJSONObject(0);

            String user = jsonObject.optString("nombre");

            switch (user){
                case "WS no retorna":               Toast.makeText(getApplicationContext(),"No se podido establecer conexion con el servidor",Toast.LENGTH_SHORT).show();
                    break;
                case "No Registra":                 Toast.makeText(getApplicationContext(),"No se ha podido completar el registro",Toast.LENGTH_SHORT).show();
                    break;
                case "No existe":                   Toast.makeText(getApplicationContext(),"El usuario introducido no existe",Toast.LENGTH_SHORT).show();
                    break;
                case "Contrasena incorrecta":       Toast.makeText(getApplicationContext(),"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
                    break;

                default:                            acceder = new Intent(getApplicationContext(),MainActivity.class);
                                                    acceder.putExtra("Usuario",user);
                                                    startActivity(acceder);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        usuario.setFocusable(true);
        usuario.setFocusableInTouchMode(true);
        contrasena.setFocusable(true);
        contrasena.setFocusableInTouchMode(true);




    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.setVisibility(View.GONE);

        usuario.setFocusable(true);
        usuario.setFocusableInTouchMode(true);
        contrasena.setFocusable(true);
        contrasena.setFocusableInTouchMode(true);

        Toast.makeText(getApplicationContext(),"Se ha producido un error.",Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
    }


}