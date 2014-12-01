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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.MovieAdapter;
import com.milansamardzic.ms.client.SortAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ms on 11/4/14.
 */
public abstract class Sve extends Fragment{
    private static final int MODE_PRIVATE = 0;
    private ListView lvMovies;
    private SortAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    SwipeRefreshLayout swipeLayout;
    Button btnNxt;
    TinyDB tinydb;
    public int adapter=0;
    MovieAdapter adapterMovies1;
    ProgressBar load;
    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();
    public int i=0;
    ImageView ivSad;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.movie_list, container, false);

        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        load = (ProgressBar) rootView.findViewById(R.id.pbLoad);

        waitToLoad();

        ivSad =(ImageView) rootView.findViewById(R.id.ivSad);


        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new SortAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);
        String ll = link();
       fetchMovies(ll);
/*
        if(fetchMovies(ll)==true)
        { ivSad.setVisibility(View.VISIBLE);}else {ivSad.setVisibility(View.GONE);}*/

        setupMovieSelectedListener();
        //    setupMovieSelectedLongListener();

        return rootView;
    }

    public  void waitToLoad(){

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(500);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvMovies.setVisibility(View.VISIBLE);
                        load.setVisibility(View.GONE);

                    }
                });
            }

            ;
        };
        thread.start();
    }

    public abstract String link();

    ArrayList<Movie> movies;
    int bool =0;
    public void fetchMovies(final String linkURL) {
        String url = linkURL;
        final ArrayList<Movie> testLista = new ArrayList<Movie>();
        client = new RottenTomatoesClient();//
        client.getMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {

                    items = body.getJSONArray("movies");

                    movies = Movie.fromJson(items);
                    ArrayList<Movie> integ = new ArrayList<Movie>();

                    for(Movie m : movies) {
                        integ.add(m);
                    }

                    Log.d("box", linkURL);
                    if (linkURL.contains("box_office"))
                    {
                        ArrayList<Movie> aMovies = new ArrayList<Movie>();
                        try {
                            adapterMovies1 = new MovieAdapter(getActivity().getBaseContext(), aMovies);
                            lvMovies.setAdapter(adapterMovies1);
                            adapter = 1;
                            for (Movie m : integ) {
                                Log.d("box", integ.toString());
                                adapterMovies1.add(m);
                            }
                    } catch (Exception e) {e.printStackTrace();}

                    }else {
                        Collections.sort(integ, new CustomComparator());
                        for (Movie m : integ) {
                            adapterMovies.add(m);
                        }
                    }
               // bool =1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);

      /*  SharedPreferences.Editor firstTimeOnFav = this.getActivity().getPreferences(MODE_PRIVATE).edit();
        SharedPreferences count = this.getActivity().getPreferences(MODE_PRIVATE);
        Boolean isIt = count.getBoolean("selection-first", false);
        if (isIt == false) {
            ViewTarget viewTarget = new ViewTarget(lvMovies);
            ShowcaseView showcaseView;
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(viewTarget)
                    .setContentTitle("One tap")
                    .setContentText("One tap on each card open detail view")
                    .build();
            firstTimeOnFav.putBoolean("selection-first", true);
            firstTimeOnFav.apply();
        }*/
       // if (bool == 1) {return true;}else{return false;}
    }

    private void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                recentList(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);

                if (adapter==1)
                {
                    i.putExtra(MOVIE_DETAIL_KEY, adapterMovies1.getItem(position));
                }
                else {
                    i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                }
                startActivity(i);
            }
        });
    }


    /*
    public void  setupMovieSelectedLongListener() {
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
    */



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

       //   duplicated movie are allowed :-)
            if(jsonA.length()>6){
                for (int e = jsonA.length()-1;  e > jsonA.length()-6; e--) {
                    if (mojaLista.get(e).getTitle().contentEquals(movies.get(position).getTitle())) {
                        Log.d("State", "true");
                        helper = 1;}
                    }
            }else{
                for (int e = 0;  e < jsonA.length(); e++) {
                    if (mojaLista.get(e).getTitle().contentEquals(movies.get(position).getTitle())) {
                        Log.d("State", "true");
                        helper = 1;}
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
            //Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();

        }
    }


}


