package com.example.moviestar.moviestar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.moviestar.moviestar.Entidades.Pelicula;

public class InfoPeliculas extends AppCompatActivity {

    TextView t;
    Pelicula pelicula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_peliculas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInfo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);



       t = findViewById(R.id.pruebat);

        pelicula = getIntent().getExtras().getParcelable("Peli");

        t.setText(pelicula.getTitulo());
    }
}
