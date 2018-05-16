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
import com.example.moviestar.moviestar.Encrypter.RSAEncrypt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingUpActivity extends AppCompatActivity {
    TextView login;

    EditText nombre,email,contrasena,repetircontrasena;
    Button registrarse;
    ProgressBar progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Intent abrirLogin;
    String Tnombre, Temail,Tcontrasena,clave,id;
    String EncryptedNombre, EncryptedEmail,EncryptedContrasena;
    int flag = 0;

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


        String url = getString(R.string.url);

        url = url + "/Claves.php";


        jsonObjectRequest = null;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.getJSONArray("PK");

                    JSONObject json2 = json.getJSONObject(0);

                    id = String.valueOf(json2.optInt("id"));

                    if (!id.equals("No se ha podido generar la clave")) {
                        clave = json2.optString("clave");
                        flag = 1;
                    } else {
                        flag = 0;
                    }


                    if(flag == 1) {
                        RSAEncrypt rsa = new RSAEncrypt(clave);
                        Tnombre = nombre.getText().toString();

                        try {
                            EncryptedNombre = rsa.encrypt(Tnombre);
                            EncryptedNombre = EncryptedNombre.replace("\n", "").replace("+", "@");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Temail = email.getText().toString();

                        try {
                            EncryptedEmail = rsa.encrypt(Temail);
                            EncryptedEmail = EncryptedEmail.replace("\n", "").replace("+", "@");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Tcontrasena = contrasena.getText().toString();

                        try {
                            EncryptedContrasena = rsa.encrypt(Tcontrasena);
                            EncryptedContrasena = EncryptedContrasena.replace("\n", "").replace("+", "@");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String url = getString(R.string.url);
                        url = url + "/RegistrarUsuario.php";


                        JSONObject parametros = new JSONObject();
                        try {
                            parametros.put("nombre", EncryptedNombre);
                            parametros.put("email", EncryptedEmail);
                            parametros.put("contrasena", EncryptedContrasena);
                            parametros.put("id", id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        jsonObjectRequest = null;
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
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
                        }, new Response.ErrorListener() {
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
                        });

                        RequestQueue request2 = Volley.newRequestQueue(getApplicationContext());
                        request2.add(jsonObjectRequest);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No se ha podido completar el registro", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                flag = 0;
            }
        });
        request.add(jsonObjectRequest);

    }
}
