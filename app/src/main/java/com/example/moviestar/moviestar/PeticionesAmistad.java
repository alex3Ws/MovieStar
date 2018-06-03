package com.example.moviestar.moviestar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterAmigos;
import com.example.moviestar.moviestar.Entidades.Amigo;

import java.util.ArrayList;

public class PeticionesAmistad extends AppCompatActivity {

    int user_id;
    RecyclerView recyclerAmigos;
    LinearLayoutManager manager;
    RecyclerViewAdapterAmigos adapter;
    ArrayList<Amigo> amigosArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiones_amistad);


        amigosArray = getIntent().getParcelableArrayListExtra("peticiones");

        user_id = getIntent().getIntExtra("user_id",0);

        recyclerAmigos = findViewById(R.id.recyclerPeticiones);

        manager = new LinearLayoutManager(getApplicationContext());
        recyclerAmigos.setHasFixedSize(true);
        recyclerAmigos.setLayoutManager(manager);

        adapter = new RecyclerViewAdapterAmigos(this, amigosArray, user_id,"peticiones",this);
        recyclerAmigos.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(),"Por favor, gestione todas sus peticiones de amistad",Toast.LENGTH_SHORT).show();
    }
}
