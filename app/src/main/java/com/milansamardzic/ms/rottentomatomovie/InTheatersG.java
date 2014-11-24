package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.MoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.gridview.GridAdapter;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 11/13/14.
 */
public class InTheatersG extends Fragment{
    GridView gwTBO;
    private GridAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    ArrayList<Movie> mojaLista;
    private TextView vstup;
    private Bundle savedState = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.in_theaters, container, false);

        String inTheatreLimit = "50";

        gwTBO = (GridView) rootView.findViewById(R.id.gvtbo);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new GridAdapter(getActivity().getBaseContext(), aMovies);
        gwTBO.setAdapter(adapterMovies);
        //gwTBO.setOnItemClickListener(this);
        String ll = "lists/movies/in_theaters.json?page_limit="+ inTheatreLimit +"&page=1&country=us";

        fetchMovies(ll);
        setupMovieSelectedListener();
        //setupMovieSelectedLongListener();

        return rootView;
    }


    ArrayList<Movie> movies;
    public void fetchMovies(String urlLink) {
        String url = urlLink;
        client = new RottenTomatoesClient();//
        client.getMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {

                    items = body.getJSONArray("movies");

                    //---ne radi---//
                    //ArrayList<Movie> movies = Movie.fromJson(items);
                    movies = Movie.fromJson(items);
                    adapterMovies.addAll(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    private void setupMovieSelectedListener() {
        gwTBO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

   /*depricated
    Movie fav;

    public void  setupMovieSelectedLongListener() {

        gwTBO.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new GsonBuilder().create();

                TinyDB tinydb = new TinyDB(getActivity());
                String str = tinydb.getString("jsonArray");
                int helper = 0;

                JSONArray jsonA = null;

                try {
                    jsonA = new JSONArray(str);
                    mojaLista = new ArrayList<Movie>();
                    for (int j = 0; j < jsonA.length(); j++) {
                        Movie m = new Movie();
                        JSONObject object = null;
                        object = (JSONObject) jsonA.get(j);
                        m.populateFrom(object);
                        mojaLista.add(m);
                        Log.d("State", "reading");
                    }

                    for (int e = 0; e < mojaLista.size(); e++) {
                        if (mojaLista.get(e).getTitle().contentEquals(movies.get(position).getTitle())) {
                            Toast.makeText(getActivity(), "Already in favourite", Toast.LENGTH_SHORT).show();
                            Log.d("State", "true");
                            helper = 1;

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (helper == 0) {
                    fav = movies.get(position);
                    mojaLista.add(fav);
                    JsonArray jsonArraySave = gson.toJsonTree(mojaLista).getAsJsonArray();
                    tinydb = new TinyDB(getActivity());
                    tinydb.putString("jsonArray", jsonArraySave.toString());
                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                    Log.d("State", "saved");


                }

                return false;
            }

        });

    }
 */






}
