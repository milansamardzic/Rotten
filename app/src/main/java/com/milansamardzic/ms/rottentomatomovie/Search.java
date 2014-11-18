package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.milansamardzic.ms.client.MoviesAdapter;
import com.milansamardzic.ms.client.RottenTomatoesClient;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by ms on 11/4/14.
 */
public class Search extends Fragment{
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    public ArrayList<String> mojaLista = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.search_layout, container, false);

            lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
            ArrayList<Movie> aMovies = new ArrayList<Movie>();
            adapterMovies = new MoviesAdapter(getActivity().getBaseContext(), aMovies);
            lvMovies.setAdapter(adapterMovies);

            setupMovieSelectedListener();
            setupMovieSelectedLongListener();

            Button btn = (Button) rootView.findViewById(R.id.search_button_sb);
            EditText tvSearch = (EditText) rootView.findViewById(R.id.searchTerm);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterMovies.clear();
                    EditText tvSearch = (EditText) rootView.findViewById(R.id.searchTerm);
                    String searchTerm = tvSearch.getText().toString();
                    String str = searchTerm.replaceAll(" ", "+");
                    String ll = "movies.json?q=" + str + "&page_limit=10&page=1";
                    fetchMovies(ll);
                }
            });


            tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        adapterMovies.clear();
                        ImageView iv = (ImageView) rootView.findViewById(R.id.smalyeSearch);
                        iv.setVisibility(View.GONE);
                        EditText tvSearch = (EditText) rootView.findViewById(R.id.searchTerm);
                        String searchTerm = tvSearch.getText().toString();
                        String str = searchTerm.replaceAll(" ", "+");
                        String ll = "movies.json?q=" + str + "&page_limit=10&page=1";
                        fetchMovies(ll);
                        return true;
                    }
                    return false;
                }
            });
            return rootView;

        }

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

                    ArrayList<Movie> movies = Movie.fromJson(items);
                    for (Movie movie : movies) {
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

                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });

    }

    private void  setupMovieSelectedLongListener() {
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String ime = movies.get(position).getTitle();
                Toast.makeText(getActivity(), "Naslov:\n"+ ime, Toast.LENGTH_LONG).show();

                //   Intent i = new Intent(getActivity(), MainActivity.class);
                //  i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                // startActivity(i);
                return false;
            }

        });

    }
}
