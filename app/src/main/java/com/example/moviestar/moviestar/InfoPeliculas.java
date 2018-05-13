package com.example.moviestar.moviestar;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestar.moviestar.Entidades.Pelicula;
import com.example.moviestar.moviestar.Fragments.FCriticasPeli;
import com.example.moviestar.moviestar.Fragments.FDatosPeli;
import com.example.moviestar.moviestar.Fragments.FTrailerPeli;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Timer;
import java.util.TimerTask;


public class InfoPeliculas extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String id;
    JSONObject jsonObject;
    String urlBaseImagenes = "http://image.tmdb.org/t/p/w500";
    TextView titulo;
    ImageView fondo, imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_peliculas);




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.contenedorPelis);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInfo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        int myColor = Color.parseColor("#303F9F");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(myColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(myColor);
        }

        request =  Volley.newRequestQueue(getApplicationContext());

        titulo = findViewById(R.id.tvTitulo);
        fondo = findViewById(R.id.ivfondo);
        imagen = findViewById(R.id.imcaratula);

        id = getIntent().getStringExtra("id");

        tabLayout.addTab(tabLayout.newTab().setText("Datos"));
        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.addTab(tabLayout.newTab().setText("Criticas"));

        llamarApi();

    }

    private void llamarApi() {

        final JSONObject prueba;
        String url  = "https://api.themoviedb.org/3/movie/"+id+"?api_key=a2424ed363ead46acaa726cf8cb45bad&language=es-ES";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(),"AHHHH",Toast.LENGTH_SHORT).show();

        jsonObject = response;

        Picasso.get().load(urlBaseImagenes+response.optString("backdrop_path")).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.cinefondo)).error(getApplicationContext().getResources().getDrawable(R.drawable.cinefondo)).resize(1100,605).into(fondo);


        Picasso.get().load(urlBaseImagenes+response.optString("poster_path")).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.cinefondo)).error(getApplicationContext().getResources().getDrawable(R.drawable.cinefondo)).resize(520,670).into(imagen);

        titulo.setText(jsonObject.optString("title"));



    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_peliculas, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            Bundle info = new Bundle();


                switch (position){

                    case 0:

                            FDatosPeli fDatosPeli = new FDatosPeli();

                            //String jsonString = jsonObject.toString();
                            //info.putString("jsonObject", jsonString);
                            fDatosPeli.setArguments(info);

                            return fDatosPeli;


                    case 1:

                            FTrailerPeli fTrailerPeli = new FTrailerPeli();
                            info.putString("id",id);
                            fTrailerPeli.setArguments(info);

                            return fTrailerPeli;

                    case 2:
                            FCriticasPeli fCriticasPeli = new FCriticasPeli();
                            return fCriticasPeli;

                }

                return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){

                case 0:  return "Datos";

                case 1:  return "Trailer";

                case 2:  return  "Criticas";

            }

            return null;
        }
    }
}
