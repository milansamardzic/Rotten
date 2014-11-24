package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.MoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 11/4/14.
 */
public abstract class Sve extends Fragment {
    private static final int MODE_PRIVATE = 0;
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    SwipeRefreshLayout swipeLayout;
    Button btnNxt;
    TinyDB tinydb;
    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.movie_list, container, false);

        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MoviesAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);
        String ll = link();
        fetchMovies(ll);
        setupMovieSelectedListener();
    //    setupMovieSelectedLongListener();

        return rootView;
    }



    public abstract String link();


    public void onRefresh() {
        Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }




//    String url;// = "lists/movies/box_office.json";
    ArrayList<Movie> movies;
    public void fetchMovies(String nestoo) {
        String url = nestoo;
        final ArrayList<Movie> testLista = new ArrayList<Movie>();
        client = new RottenTomatoesClient();//
        client.getMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {

                    items = body.getJSONArray("movies");

                    movies = Movie.fromJson(items);

                    for (Movie movie : movies) {
                        adapterMovies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);


        SharedPreferences.Editor firstTimeOnFav = this.getActivity().getPreferences(MODE_PRIVATE).edit();
        SharedPreferences count = this.getActivity().getPreferences(MODE_PRIVATE);
        Boolean isIt = count.getBoolean("selection-end", false);
        if (isIt == false) {
            ViewTarget viewTarget = new ViewTarget(lvMovies);
            ShowcaseView showcaseView;
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(viewTarget)
                    .setContentTitle("One tap")
                    .setContentText("One tap on each card open detail view")
                    .build();
            firstTimeOnFav.putBoolean("selection-end", true);
            firstTimeOnFav.apply();
        }
        
    }


    private void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                recentList(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }
//del idiote!
    public void  setupMovieSelectedLongListener() {

        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }

        });

    }
 //---


    public void recentList(int position) {
        Gson gson = new GsonBuilder().create();
        Movie fav;

        TinyDB tinydb = new TinyDB(getActivity());
        String str = tinydb.getString("jsonArrayRecent");
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
            tinydb.putString("jsonArrayRecent", jsonArraySave.toString());
            Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
            Log.d("State", "saved");

        }
    }


}


