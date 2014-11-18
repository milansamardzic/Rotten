/* didn use anymore

package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.BoxOfficeMoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.BoxOfficeMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


 * Created by ms on 10/24/14.

public class TopBoxOffice extends Fragment{
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    SwipeRefreshLayout swipeLayout;
    Integer number=20;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_box_office, container, false);
        setHasOptionsMenu(true);

       lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(getActivity().getBaseContext(), aMovies);
        lvMovies.setAdapter(adapterMovies);

        fetchBoxOfficeMovies(number);
        setupMovieSelectedListener();
        setupMovieSelectedLongListener();

     //   GridView gwTBO = (GridView) rootView.findViewById(R.id.gvtbo);
    //   gwTBO.setAdapter(new GridAdapter(getActivity()));
     //  gwTBO.setOnItemClickListener(this);
     /*   lvMovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            public int currentScrollState;
            public int currentFirstVisibleItem;
            public int currentVisibleItemCount;
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /*** In this way I detect if there's been a scroll which has completed ***/
                    /***  the work! ***/
/*
                    adapterMovies.clear();
                    number+=1;
                    fetchBoxOfficeMovies(number);
                    Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button b = (Button) rootView.findViewById(R.id.reffb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterMovies.clear();
                number+=5;
                fetchBoxOfficeMovies(number);
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
            }
        });  s
        return rootView;
    }

    public void fetchBoxOfficeMovies(Integer newNumber) {
        String url = "lists/movies/box_office.json?limit="+ newNumber.toString() + "&country=us";
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = body.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    // Load model objects into the adapter
                    for (BoxOfficeMovie movie : movies) {
                        adapterMovies.add(movie);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }


    private void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

              Intent i = new Intent(getActivity(), BoxOfficeDetailActivity.class);
              i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
               startActivity(i);
            }
        });



        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(getActivity(), "favourites", Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    private void  setupMovieSelectedLongListener(){
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
                return false;
            }
        });


    }

}
*/