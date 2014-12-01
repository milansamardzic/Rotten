package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.milansamardzic.ms.client.MovieAdapter;
import com.milansamardzic.ms.client.SortAdapter;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.milansamardzic.ms.rottentomatomovie.R.layout.*;

/**
 * Created by ms on 11/3/14.
 */
public class Favourite extends Fragment {
    private static final int MODE_PRIVATE = 0;
    private ListView lvMovies;
    public MovieAdapter adapterMovies;
    ArrayList<Movie> listdata;

    public static final String MOVIE_DETAIL_KEY = "movie";
    ArrayList<String> titlee;
    Movie m;
    ProgressBar load;
    ImageView ivSad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(movie_list, container, false);

        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MovieAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);
        load = (ProgressBar) rootView.findViewById(R.id.pbLoad);
        ivSad =(ImageView) rootView.findViewById(R.id.ivSad);
            if(loadData()==false)
            {
                ivSad.setVisibility(View.VISIBLE);
            }else {ivSad.setVisibility(View.GONE);}

        removeFromFavourites();

        setupMovieSelectedListener();
        waitToLoad();

        return rootView;
    }

    public boolean loadData(){

        TinyDB tinydb = new TinyDB(getActivity());
        Gson gson = new Gson();
        Log.d("test", tinydb.getString("jsonArray"));

        String strJson = tinydb.getString("jsonArray");
        if (strJson != null) {
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                //  listdata = new ArrayList<Movie>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        m = new Movie();
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        m.populateFrom(object);
                        //       listdata.add(m);
                        adapterMovies.add(m);

                        Log.d("procitao", m.getTitle() + " " + m.getDuration() + m.getLargePosterUrl());

                        SharedPreferences.Editor firstTimeOnFav = this.getActivity().getPreferences(MODE_PRIVATE).edit();
                        SharedPreferences count = this.getActivity().getPreferences(MODE_PRIVATE);
                        Boolean isIt = count.getBoolean("fav", false);
                        if (isIt == false) {
                            ViewTarget viewTarget = new ViewTarget(lvMovies);
                            ShowcaseView showcaseView;
                            showcaseView = new ShowcaseView.Builder(getActivity())
                                    .setTarget(viewTarget)
                                    .setContentTitle("One or Two tap?")
                                    .setContentText("One tap on each card open detail view, but BE CAREFUL - LONG PRESS DELETE MOVIE FROM LIST")
                                    .setStyle(R.style.customShowCase)
                                    .build();
                            firstTimeOnFav.putBoolean("fav", true);
                            firstTimeOnFav.apply();
                        }
                        return true;
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    private void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
         refreshList();
    }

    private void refreshList() {

        Gson gson = new GsonBuilder().create();
        TinyDB tinydb = new TinyDB(getActivity());
        String str = tinydb.getString("jsonArray");

        if (str != null) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(str);
                mojaLista = new ArrayList<Movie>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Movie m = new Movie();
                    JSONObject object = null;
                    object = (JSONObject) jsonArray.get(i);
                    m.populateFrom(object);
                    mojaLista.add(m);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
        }
        JsonArray jsonArray = gson.toJsonTree(mojaLista).getAsJsonArray();
        tinydb = new TinyDB(getActivity());
        tinydb.putString("jsonArray", jsonArray.toString());

        //-refresh-list-//
        adapterMovies.clear();
        String strJson = tinydb.getString("jsonArray");
        if (strJson != null) {
            try {
                JSONArray jsonArrayRefresh = new JSONArray(strJson);
                //  listdata = new ArrayList<Movie>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArrayRefresh.length(); i++) {
                        Movie m = new Movie();
                        JSONObject object = (JSONObject) jsonArrayRefresh.get(i);
                        m.populateFrom(object);
                        //       listdata.add(m);
                        adapterMovies.add(m);
                        Log.d("procitao", m.getTitle() + " " + m.getDuration());
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();

    public void removeFromFavourites() {
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new GsonBuilder().create();
                TinyDB tinydb = new TinyDB(getActivity());
                String str = tinydb.getString("jsonArray");

                if (str != null) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(str);
                        mojaLista = new ArrayList<Movie>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Movie m = new Movie();
                            JSONObject object = null;
                            object = (JSONObject) jsonArray.get(i);
                            m.populateFrom(object);
                            mojaLista.add(m);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // mojaLista.remove(position);
                }
                mojaLista.remove(position);
                JsonArray jsonArray = gson.toJsonTree(mojaLista).getAsJsonArray();
                tinydb = new TinyDB(getActivity());
                tinydb.putString("jsonArray", jsonArray.toString());
                Toast.makeText(getActivity(), "Movie is deleted", Toast.LENGTH_SHORT).show();

                //-refresh-list-//
                adapterMovies.clear();
                String strJson = tinydb.getString("jsonArray");
                if (strJson != null) {
                    try {
                        JSONArray jsonArrayRefresh = new JSONArray(strJson);
                        //  listdata = new ArrayList<Movie>();
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArrayRefresh.length(); i++) {
                                Movie m = new Movie();
                                JSONObject object = (JSONObject) jsonArrayRefresh.get(i);
                                m.populateFrom(object);
                                //       listdata.add(m);
                                adapterMovies.add(m);
                                Log.d("procitao", m.getTitle() + " " + m.getDuration());
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

        });

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



}