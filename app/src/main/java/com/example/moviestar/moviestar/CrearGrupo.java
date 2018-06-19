package com.example.moviestar.moviestar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterAmigos;
import com.example.moviestar.moviestar.Adapter.RecyclerViewGruposAdapter;
import com.example.moviestar.moviestar.Entidades.Amigo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class CrearGrupo extends AppCompatActivity {

    ImageView bfecha;
    EditText tbfecha;
    int user_id;
    RecyclerView recyclerAmigosGrupos;
    LinearLayoutManager manager;
    RecyclerViewGruposAdapter adapter;
    ArrayList<Amigo> amigos;
    RequestQueue request;
    TextView amigosAñadidos;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        user_id = getIntent().getIntExtra("user_id",0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCrearGrupo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);



        int myColor = Color.parseColor("#303F9F");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(myColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(myColor);
        }

        bfecha = findViewById(R.id.bfecha);
        tbfecha = findViewById(R.id.tbfecha);

        bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog date = new DatePickerDialog(CrearGrupo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String fecha = dayOfMonth+"/"+month+"/"+year;

                        tbfecha.setText(fecha);

                    }
                }, year, month, day);

                calendar.add(Calendar.MONTH,2);

                date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                date.show();
            }
        });

        amigos = new ArrayList<>();
        recyclerAmigosGrupos = findViewById(R.id.recyclerAmigosGrupo);

        manager = new LinearLayoutManager(getApplicationContext());
        recyclerAmigosGrupos.setHasFixedSize(true);
        recyclerAmigosGrupos.setLayoutManager(manager);

        adapter = new RecyclerViewGruposAdapter(getApplicationContext(),amigos,user_id,"amigos",amigosAñadidos);
        recyclerAmigosGrupos.setAdapter(adapter);

        request = Volley.newRequestQueue(getApplicationContext());

        recuperarAmigos();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void recuperarAmigos() {

        String url = getString(R.string.url);

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user", user_id);
            parametros.put("aceptado",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "/ConsultarAmigos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if(response.optString("error") == "WS no retorna"){

                        Toast.makeText(getApplicationContext(),"No se pudo recuperar la lista de amigos",Toast.LENGTH_SHORT).show();

                    }
                    else if (response.has("amigos")){

                        JSONArray jsonArray = response.getJSONArray("amigos");

                        Toast.makeText(getApplicationContext(),"okkkk",Toast.LENGTH_SHORT).show();

                        for(int i = 0; i<jsonArray.length(); i++){

                            JSONObject jsonObject = null;
                            jsonObject = jsonArray.optJSONObject(i);

                            Amigo amigo = new Amigo();

                            amigo.setId(Integer.parseInt(jsonObject.optString("id_user_amigo")));
                            amigo.setNombreUsuario(jsonObject.optString("nombre"));

                            amigos.add(amigo);

                        }
                        adapter.notifyDataSetChanged();


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"koooo",Toast.LENGTH_SHORT).show();

            }
        });

        request.add(jsonObjectRequest);


    }


}
