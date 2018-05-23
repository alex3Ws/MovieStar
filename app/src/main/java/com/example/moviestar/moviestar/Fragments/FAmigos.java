package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapter;
import com.example.moviestar.moviestar.Adapter.RecyclerViewAdapterAmigos;
import com.example.moviestar.moviestar.Entidades.Amigo;
import com.example.moviestar.moviestar.R;

import java.util.ArrayList;

public class FAmigos extends Fragment {

    View vista;
    RecyclerView recyclerAmigos;
    LinearLayoutManager manager;
    RecyclerViewAdapterAmigos adapter;
    ArrayList<Amigo> amigos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_amigos, container, false);

        recyclerAmigos = vista.findViewById(R.id.recyclerAmigos);

        manager = new LinearLayoutManager(getContext());
        recyclerAmigos.setHasFixedSize(true);
        recyclerAmigos.setLayoutManager(manager);

        amigos = new ArrayList<>();


        Amigo amigo = new Amigo();

        amigo.setNombreUsuario("alex3Ws");
        amigo.setPais("España");

        amigos.add(amigo);

        Amigo amigo2 = new Amigo();

        amigo2.setNombreUsuario("saliitoo");
        amigo2.setPais("Cuba");

        amigos.add(amigo2);

        adapter = new RecyclerViewAdapterAmigos(getContext(),amigos,3);
        recyclerAmigos.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        return vista;
    }


}
