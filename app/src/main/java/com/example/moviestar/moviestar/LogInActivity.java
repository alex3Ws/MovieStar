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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Encrypter.RSAEncrypt;

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
    String id,clave,Tnombre,Tcontrasena,EncryptedNombre,EncryptedContrasena;

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


                recuperarClave();

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


    private void recuperarClave() {

        progreso.setVisibility(View.VISIBLE);

        usuario.setFocusable(false);
        contrasena.setFocusable(false);

        String url = getString(R.string.url);



        url = url + "/Claves.php";


        jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray json = response.getJSONArray("PK");

                    JSONObject json2 = json.getJSONObject(0);

                    id = String.valueOf(json2.optInt("id"));

                    if (!id.equals("No se ha podido generar la clave")) {
                        clave = json2.optString("clave");
                        consumirWS();
                    } else {
                        usuario.setFocusable(true);
                        usuario.setFocusableInTouchMode(true);
                        contrasena.setFocusable(true);
                        contrasena.setFocusableInTouchMode(true);
                        Toast.makeText(getApplicationContext(), "No se ha podido realizar el Login", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    usuario.setFocusable(true);
                    usuario.setFocusableInTouchMode(true);
                    contrasena.setFocusable(true);
                    contrasena.setFocusableInTouchMode(true);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                usuario.setFocusable(true);
                usuario.setFocusableInTouchMode(true);
                contrasena.setFocusable(true);
                contrasena.setFocusableInTouchMode(true);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void consumirWS() {



        progreso.setVisibility(View.VISIBLE);

        usuario.setFocusable(false);
        contrasena.setFocusable(false);

        RSAEncrypt rsa = new RSAEncrypt(clave);
        Tnombre = usuario.getText().toString();

        try {
            EncryptedNombre = rsa.encrypt(Tnombre);
            EncryptedNombre = EncryptedNombre.replace("\n", "").replace("+", "@");
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

        JSONObject parametros = new JSONObject();

        try {

            parametros.put("nombre",EncryptedNombre);
            parametros.put("contrasena",EncryptedContrasena);
            parametros.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getString(R.string.url);

        url  = url +"/ConsultarUsuario.php";




        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,parametros,this,this);

        request.add(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {

        progreso.setVisibility(View.GONE);

        borrarClaves();

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
                                                    int id = jsonObject.optInt("id_user");
                                                    acceder.putExtra("user_id",id);
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

        borrarClaves();
        
        usuario.setFocusable(true);
        usuario.setFocusableInTouchMode(true);
        contrasena.setFocusable(true);
        contrasena.setFocusableInTouchMode(true);

        Toast.makeText(getApplicationContext(),"Se ha producido un error.",Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
    }


    public void borrarClaves(){

        String url = getString(R.string.url);
        url = url + "/Borrar.php?id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });

        request.add(stringRequest);
    }
}