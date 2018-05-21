package com.example.moviestar.moviestar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviestar.moviestar.R;

public class FPeliculas_vistas extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peliculas_vistas, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(),"Vistas - onResume",Toast.LENGTH_SHORT).show();
        FPeliculas_pendientes fp = new FPeliculas_pendientes();
        fp.onPause();
    }
}
