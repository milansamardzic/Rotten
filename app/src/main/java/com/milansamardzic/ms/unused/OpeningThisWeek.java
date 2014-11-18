/*
dont use anymore


package com.milansamardzic.ms.rottentomatomovie;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.BoxOfficeMoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.BoxOfficeMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


 // Created by ms on 10/24/14.

public class OpeningThisWeek extends Fragment {
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    TinyDB tinydb;
    public ArrayList<BoxOfficeMovie> mojaLista = new ArrayList<BoxOfficeMovie>();
    SwipeRefreshLayout swipeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_box_office, container, false);


        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);

        fetchBoxOfficeMovies();
        setupMovieSelectedListener();
        setupMovieSelectedLongListener();


          // GridView gwTBO = (GridView) rootView.findViewById(R.id.gvtbo);
         //  gwTBO.setAdapter(new GridAdapter(getActivity()));
        //    gwTBO.setOnItemClickListener(this);


        return rootView;
    }

    ArrayList<BoxOfficeMovie> movies;
    public void fetchBoxOfficeMovies() {
        String url = "lists/movies/opening.json?limit=1";
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {
                    items = body.getJSONArray("movies");
                    movies = BoxOfficeMovie.fromJson(items);
                    adapterMovies.addAll(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("opening", movies.toString());
                }
            }
        }, url);
    }




    public void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
               // PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("MYLABEL", "defaultStringIfNothingFound");



             //   Intent i = new Intent(getActivity(), BoxOfficeDetailActivity.class);
               // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
               // startActivity(i);
            }
        });

    }

    public void  setupMovieSelectedLongListener() {

        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new GsonBuilder().create();
                BoxOfficeMovie bmo;
                bmo = movies.get(position);

                mojaLista.add(bmo);

                JsonArray jsonArray = gson.toJsonTree(mojaLista).getAsJsonArray();

                tinydb = new TinyDB(getActivity());
                tinydb.putString("jsonArray", jsonArray.toString());


                 return false;
            }

        });

    }


}

*/