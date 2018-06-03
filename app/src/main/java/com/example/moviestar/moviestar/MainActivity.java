package com.example.moviestar.moviestar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.Fragments.FBusqueda_Pelicula;
import com.example.moviestar.moviestar.Fragments.FPeliculas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView prueba;
    boolean flag = false;
    public String user_name;
    public int user_id;
    Bundle info;
    MaterialSearchView materialSearchView;
    NavigationView navigationView;
    final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    RequestQueue request;
    ArrayList<Amigo> amigosArray;

    boolean peticionesAmistad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        materialSearchView = findViewById(R.id.busqueda);

        //user_name = getIntent().getStringExtra("Usuario");
        user_id = getIntent().getIntExtra("user_id",0);

        amigosArray = new ArrayList<>();
        request = Volley.newRequestQueue(getApplicationContext());


        Bundle info2 = new Bundle();
        info2.putInt("id",user_id);

        FPeliculas fPeliculas = new FPeliculas();
        fPeliculas.setArguments(info2);

        fragmentManager.beginTransaction().replace(R.id.contenedor, fPeliculas).commit();


        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                flag = true;

                FBusqueda_Pelicula fBusqueda_pelicula = new FBusqueda_Pelicula();

                Bundle info = new Bundle();
                info.putInt("user_id", user_id);
                info.putString("query",query);
                fBusqueda_pelicula.setArguments(info);

                fragmentManager.beginTransaction().replace(R.id.contenedor, fBusqueda_pelicula).commit();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.busqueda, menu);

        MenuItem item = menu.findItem(R.id.search);
        materialSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(flag) {
                info = null;
                info = new Bundle();
                info.putInt("id",user_id);

                FPeliculas fPeliculas = new FPeliculas();
                fPeliculas.setArguments(info);

                fragmentManager.beginTransaction().replace(R.id.contenedor, fPeliculas).commit();
                flag = false;
            }
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        /*if(!flag){


        }*/

        info = null;
        info = new Bundle();
        info.putInt("id",user_id);

        if (id == R.id.nav_camera) {



            FPeliculas fPeliculas = new FPeliculas();
            fPeliculas.setArguments(info);

            fragmentManager.beginTransaction().replace(R.id.contenedor, fPeliculas).commit();

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(getBaseContext(),AreaPersonal.class);
            intent.putExtra("user_id",user_id);
            startActivity(intent);






        } else if (id == R.id.nav_slideshow) {
            Intent intent;


            if(peticionesAmistad) {
                intent = new Intent(getApplicationContext(),PeticionesAmistad.class);
                intent.putExtra("peticiones", amigosArray);


            }
            else{

                intent = new Intent(getApplicationContext(),AreaAmigos.class);

            }
            intent.putExtra("user_id",user_id);

            startActivity(intent);






        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void  comprobarPeticiones() {

        String url = getString(R.string.url);

        amigosArray.clear();

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("id_user", user_id);
            parametros.put("aceptado",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "/ConsultarAmigos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.has("amigos")){

                        peticionesAmistad = true;
                        JSONArray jsonArray = response.getJSONArray("amigos");


                        for(int i = 0; i<jsonArray.length(); i++){

                            JSONObject jsonObject = null;
                            jsonObject = jsonArray.optJSONObject(i);

                            Amigo amigo = new Amigo();

                            amigo.setId(Integer.parseInt(jsonObject.optString("id_user")));
                            amigo.setNombreUsuario(jsonObject.optString("nombre"));

                            amigosArray.add(amigo);

                        }

                        MenuItem item = navigationView.getMenu().getItem(2);

                        RelativeLayout rl = (RelativeLayout) item.getActionView();

                        rl.setVisibility(View.VISIBLE);
                        TextView tv = rl.findViewById(R.id.numeroNotificacion);
                        tv.setText(String.valueOf(amigosArray.size()));

                    }
                    else
                    {
                        peticionesAmistad = false;
                        MenuItem item = navigationView.getMenu().getItem(2);

                        RelativeLayout rl = (RelativeLayout) item.getActionView();

                        rl.setVisibility(View.GONE);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"dsgads",Toast.LENGTH_SHORT).show();

            }
        });

        request.add(jsonObjectRequest);


    }

    @Override
    protected void onResume() {
        super.onResume();

       comprobarPeticiones();


    }
}
