package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestar.moviestar.CrearGrupo;
import com.example.moviestar.moviestar.R;

public class FGrupos extends Fragment {

    FloatingActionButton flabAdd;
    View vista;
    int user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_grupos, container, false);

        user_id = getArguments().getInt("id");


        flabAdd = vista.findViewById(R.id.flabAdd);
        flabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crearGrupo = new Intent(getContext(), CrearGrupo.class);
                crearGrupo.putExtra("user_id",user_id);

                startActivity(crearGrupo);


            }
        });

        return vista;


    }


}
