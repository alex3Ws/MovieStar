package com.example.moviestar.moviestar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.example.moviestar.moviestar.Encrypter.Rsa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingUpActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    TextView login;

    EditText nombre,email,contrasena,repetircontrasena;
    Button registrarse;
    ProgressBar progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Intent abrirLogin;
    String Tnombre, Temail,Tcontrasena;
    String EncryptedNombre, EncryptedEmail,EncryptedContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        login = findViewById(R.id.tvlogin);
        nombre = findViewById(R.id.tbuser);
        email = findViewById(R.id.tbemail);
        contrasena = findViewById(R.id.tbcontraseña);
        repetircontrasena = findViewById(R.id.tbrepetircontraseña);
        registrarse = findViewById(R.id.bsingup);
        progreso = findViewById(R.id.pbprogreso2);

        request = Volley.newRequestQueue(getApplicationContext());

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombre.getText().toString().isEmpty() || email.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty()
                        || repetircontrasena.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(contrasena.getText().toString().equals(repetircontrasena.getText().toString())){

                        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){

                            Toast.makeText(getApplicationContext(),"Introduzca un email valido",Toast.LENGTH_SHORT).show();
                        }
                        else{


                            consumirWS();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Las contraseñas introducidas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(abrirLogin);
            }
        });

    }

    private void consumirWS() {


        progreso.setVisibility(View.VISIBLE);

        nombre.setFocusable(false);
        email.setFocusable(false);
        contrasena.setFocusable(false);
        repetircontrasena.setFocusable(false);


        Rsa codeRsa = new Rsa();

        Tnombre = nombre.getText().toString();
        EncryptedNombre = codeRsa.encryptByPublic(Tnombre);

        Temail = email.getText().toString();
        EncryptedEmail = codeRsa.encryptByPublic(Temail);

        Tcontrasena = contrasena.getText().toString();
        EncryptedContrasena = codeRsa.encryptByPublic(Tcontrasena);



        String url = getString(R.string.url);

        url  = url +"/RegistrarUsuario.php?nombre="+EncryptedNombre+
                "&email="+EncryptedEmail+"&contrasena="+EncryptedContrasena;

        /*String url = "http://192.168.140.1/WebServices_PHP/RegistrarUsuario.php?nombre="+nombre.getText().toString()+
                "&email="+email.getText().toString()+"&contrasena="+contrasena.getText().toString();*/

        url = url.replace(" ","%20");


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        request.add(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {
        String mensaje= null;
        progreso.setVisibility(View.GONE);

        try {

            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;
            jsonObject = json.getJSONObject(0);

            switch (jsonObject.optString("nombre")){
                case "WS no retorna":               mensaje = "No se podido establecer conexion con el servidor";
                                                    break;
                case "No Registra":                 mensaje = "No se ha podido completar el registro";
                                                    break;
                case "Email ya existe":             mensaje = "Ya existe un usuario registrado con ese email.";
                                                    break;
                case "Nombre de usuario ya existe": mensaje = "El nombre de usuario introducido ya esta en uso";
                                                    break;
                default:                            mensaje = "Se ha registrado correctamente";
                                                    abrirLogin = new Intent(getApplicationContext(),LogInActivity.class);
                                                    startActivity(abrirLogin);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        nombre.setFocusable(true);
        nombre.setFocusableInTouchMode(true);
        email.setFocusable(true);
        email.setFocusableInTouchMode(true);
        contrasena.setFocusable(true);
        contrasena.setFocusableInTouchMode(true);
        repetircontrasena.setFocusable(true);
        repetircontrasena.setFocusableInTouchMode(true);


        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.setVisibility(View.GONE);

        nombre.setFocusable(true);
        nombre.setFocusableInTouchMode(true);
        email.setFocusable(true);
        email.setFocusableInTouchMode(true);
        contrasena.setFocusable(true);
        contrasena.setFocusableInTouchMode(true);
        repetircontrasena.setFocusable(true);
        repetircontrasena.setFocusableInTouchMode(true);

        Toast.makeText(getApplicationContext(),"Se ha producido un error.",Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
    }
}
