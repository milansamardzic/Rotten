package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.HomeAdapter;
import com.milansamardzic.ms.client.MoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import static com.milansamardzic.ms.rottentomatomovie.R.layout.movie_list;
/**
 * Created by ms on 11/13/14.
 */
public class Home extends Fragment{
    private static final int MODE_PRIVATE = 1;
    private ListView lvMovies;
    private HomeAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_frg, container, false);

        checkIsFirstTime();

        TwoWayView twv = (TwoWayView) rootView.findViewById(R.id.lvItemsBox);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();

        adapterMovies = new HomeAdapter(getActivity().getBaseContext(), aMovies);
        twv.setAdapter(adapterMovies);


        twv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });

        TinyDB tinydb = new TinyDB(getActivity());
        Gson gson = new Gson();
        Log.d("test", tinydb.getString("jsonArray"));

        String strJson = tinydb.getString("jsonArray");
        if (strJson != null) {
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                //  listdata = new ArrayList<Movie>();
                if (jsonArray != null) {
                    for (int i = 0; i < 5; i++) {
                        Movie m = new Movie();
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        m.populateFrom(object);
                        adapterMovies.add(m);
                        Log.d("procitao", m.getTitle() + " " + m.getDuration());
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        TwoWayView twvRecent = (TwoWayView) rootView.findViewById(R.id.lvItemsRecent);
        ArrayList<Movie> eMovies = new ArrayList<Movie>();

        adapterMovies = new HomeAdapter(getActivity().getBaseContext(), eMovies);
        twvRecent.setAdapter(adapterMovies);

        twvRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });

        recentSeen();

        return rootView;
    }



    public void checkIsFirstTime() {
        SharedPreferences.Editor firstTimeOnHome = this.getActivity().getPreferences(MODE_PRIVATE).edit();
        SharedPreferences count = this.getActivity().getPreferences(MODE_PRIVATE);
        Boolean isIt = count.getBoolean("first", false);
        if (isIt == false) {
            new ShowcaseView.Builder(getActivity())
                    .setTarget(new ActionViewTarget(getActivity(), ActionViewTarget.Type.HOME))
                    .setContentTitle("Menu view")
                    .setContentText("Here is menu")
                    .hideOnTouchOutside()
                    .build();
            firstTimeOnHome.putBoolean("first", true);
            firstTimeOnHome.apply();
        }
    }

    public void recentSeen(){

        TinyDB tinydb = new TinyDB(getActivity());
        Gson gson = new Gson();
        Log.d("test", tinydb.getString("jsonArrayRecent"));

        String strJson = tinydb.getString("jsonArrayRecent");
        if (strJson != null) {
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                if (jsonArray != null) {
                    for (int i = 0; i <  jsonArray.length(); i++) {
                        Movie m = new Movie();
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        m.populateFrom(object);
                        adapterMovies.add(m);
                        Log.d("procitao", m.getTitle() + " " + m.getDuration());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
