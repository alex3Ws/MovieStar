package com.example.moviestar.moviestar;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviestar.moviestar.Fragments.FBusqueda_Pelicula;
import com.example.moviestar.moviestar.Fragments.FPeliculas;
import com.example.moviestar.moviestar.Fragments.FPeliculas_favoritas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView prueba;
    boolean flag = false;
    public String user_name;
    public int user_id;
    Bundle info;
    MaterialSearchView materialSearchView;
    final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        materialSearchView = findViewById(R.id.busqueda);

        user_name = getIntent().getStringExtra("Usuario");
        user_id = getIntent().getIntExtra("id",0);




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



        if (id == R.id.nav_camera) {

            info = null;
            info = new Bundle();
            info.putInt("id",user_id);

            FPeliculas fPeliculas = new FPeliculas();
            fPeliculas.setArguments(info);

            fragmentManager.beginTransaction().replace(R.id.contenedor, fPeliculas).commit();

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(getBaseContext(),AreaPersonal.class);
            intent.putExtra("user_id",user_id);
            startActivity(intent);




        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
