package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.SortAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ms on 11/16/14.
 */
public class About extends Fragment implements View.OnClickListener{
    int click=0;
    private SortAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    ListView lvRecRnd;
    ImageView imgV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.about, container, false);
        lvRecRnd = (ListView) rootView.findViewById(R.id.lvRnd);

        imgV = (ImageView) rootView.findViewById(R.id.splashscreen);
        imgV.setOnClickListener(this);

        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new SortAdapter(getActivity().getBaseContext(), aMovies);
        lvRecRnd.setAdapter(adapterMovies);

        return rootView;
    }
    public int n =0;
    @Override
    public void onClick(View v) {
        click++;
        if(click>=7) {
            ImageView animationTarget = (ImageView) getActivity().findViewById(R.id.splashscreen);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_around_center_point1);

            fetchMovies("lists/movies/box_office.json?limit=50&country=us");
            setupMovieSelectedListener();
            Toast.makeText(getActivity(), "Angry give recomandation for movie night!", Toast.LENGTH_SHORT).show();
            RelativeLayout about = (RelativeLayout) getActivity().findViewById(R.id.abutLogoA);
            about.setVisibility(View.GONE);
            animationTarget.startAnimation(animation);

        }
    }

    ArrayList<Movie> movies;
    public void fetchMovies(String nestoo) {
        String url = nestoo;
        imgV.setEnabled(false);
        Random rand = new Random();
        final int  n = rand.nextInt(40) + 1;
        final ArrayList<Movie> testLista = new ArrayList<Movie>();
        client = new RottenTomatoesClient();//
        client.getMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {

                    items = body.getJSONArray("movies");

                    movies = Movie.fromJson(items);
                    Movie movie;
                        adapterMovies.add( movies.get(n));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);}

    private void setupMovieSelectedListener() {

        lvRecRnd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }


    }
