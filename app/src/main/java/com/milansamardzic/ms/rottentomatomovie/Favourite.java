package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.os.Bundle;
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
import com.milansamardzic.ms.client.MoviesAdapter;
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
    private ListView lvMovies;
    public MoviesAdapter adapterMovies;
    ArrayList<Movie> listdata;

    public static final String MOVIE_DETAIL_KEY = "movie";
    ArrayList<String> titlee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(movie_list, container, false);


        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MoviesAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);

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
                        Movie m = new Movie();
                        JSONObject object = (JSONObject) jsonArray.get(i);
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
        removeFromFavourites();
        return rootView;
    }

    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();
    public void removeFromFavourites() {
            lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Gson gson = new GsonBuilder().create();
                    TinyDB tinydb = new TinyDB(getActivity());
                    String str = tinydb.getString("jsonArray");

                    if(str!=null){
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

                    }else{
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

}