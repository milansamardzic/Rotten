package com.milansamardzic.ms.rottentomatomovie;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.milansamardzic.ms.RemoteImageView;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.parseColor;

public class DetailActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageView ivPosterImage;
    private ImageView ivPosterImage1;
    private ImageView ivCritichScore;
    private TextView tvTitle;
    private TextView tvTitle1;
    private TextView tvYear;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private ProgressBar pgAB;
    private ProgressBar pgCB;
    private ActionBarDrawerToggle drawerListener;
    private FragmentManager fm;
    private Button btn;
    private Movie movie;
    private Movie fav;
    private String str;
    private Gson gson;
    private TinyDB tinydb;

    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(parseColor("#ce5043"))); //3A9425
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().getCustomView();
        btn = (Button) findViewById(R.id.favButton);

      //  Button btn = (Button) findViewById(R.id.favButton);
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        ivPosterImage1 = (ImageView) findViewById(R.id.ivPosterImage1);
        ivCritichScore = (ImageView) findViewById(R.id.ivCritichScore);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // tvTitle1 = (TextView) findViewById(R.id.tvTitle1);
        tvYear = (TextView) findViewById(R.id.tvYearDA);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        // tvCriticsConsensus = (TextView) findViewById(R.id.tvCriticsConsensus);
        tvAudienceScore = (TextView) findViewById(R.id.percentCriticsA);
        tvCriticsScore = (TextView) findViewById(R.id.percentCriticsC);
        // Load movie data
        pgAB = (ProgressBar) findViewById(R.id.progressBarAB);
        pgCB = (ProgressBar) findViewById(R.id.progressBarCB);
        Movie movie = (Movie) getIntent().getSerializableExtra(Sve.MOVIE_DETAIL_KEY);
//        BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(TopBoxOffice.MOVIE_DETAIL_KEY);

        btn.setOnClickListener(this);

        loadMovie(movie);
    }

    public Movie ee;

    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {
        ee = movie;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setTitle(movie.getTitle());
        }
        tvTitle.setText(movie.getTitle());
        pgAB.setProgress(movie.getAudienceScore());
        pgCB.setProgress(movie.getCriticsScore());
        String year = String.valueOf(movie.getYear());

        if (movie.getCriticsScore() >= 75) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_fresh));
        } else if (movie.getCriticsScore() < 50) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_bad));
        } else if (movie.getCriticsScore() >= 60 && movie.getCriticsScore() < 75) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_god));
        }

        tvYear.setText(year);

        String criticsScore = String.valueOf(movie.getCriticsScore());
        String audianceScore = String.valueOf(movie.getAudienceScore());
        tvAudienceScore.setText(audianceScore + "%");
        tvCriticsScore.setText(criticsScore + "%");
        String cast;

        cast = movie.getCastList().replaceAll(", ", "\n\n• ");
        tvCast.setText("• " + cast);

        tvSynopsis.setText(Html.fromHtml(movie.getSynopsis()));

        String imageURL = fixAPI(movie.getLargePosterUrl());

        RemoteImageView remoteImageView = (RemoteImageView) findViewById(R.id.ivPosterImage);
        remoteImageView.setImageURL(imageURL);

        RemoteImageView remoteImageView1 = (RemoteImageView) findViewById(R.id.ivPosterImage1);
        remoteImageView1.setImageURL(imageURL);

        openSharedPreferences();
        readJSON();
        for (int e = 0; e < mojaLista.size(); e++) {
            Log.d("State", movie.getTitle());
            if (mojaLista.get(e).getTitle().contentEquals(movie.getTitle())) {
                btn.setBackground(getResources().getDrawable(R.drawable.favourite_full));
                Log.d("State", "true");
            }
        }


    }

    public String fixAPI(String brokenImageLink) {
        String imageURL = brokenImageLink;
        return imageURL = imageURL.replaceAll("[//_]+[t]..", "_det"); //_org
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //.navigateUpFromSameTask(this);
                super.onBackPressed();
        }
        return false;
    }


    public void openSharedPreferences() {
        gson = new GsonBuilder().create();
        tinydb = new TinyDB(this);
    }

    public void readJSON() {
        str = tinydb.getString("jsonArray");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//delete from list
    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        int helper = 0;
        openSharedPreferences();
        readJSON();
        for (int e = 0; e < mojaLista.size(); e++) {
                if (mojaLista.get(e).getTitle().contentEquals(ee.getTitle())) {
                    //delete
                    mojaLista.remove(e);
                    JsonArray jsonArray = gson.toJsonTree(mojaLista).getAsJsonArray();
                    tinydb = new TinyDB(this);
                    tinydb.putString("jsonArray", jsonArray.toString());
                    Toast.makeText(this, "Movie is deleted", Toast.LENGTH_SHORT).show();
                    btn.setBackground(getResources().getDrawable(R.drawable.favourite_empty));
                    //delete
                    helper = 1;
                }
            }
        if (helper == 0) {
            fav = ee;
            mojaLista.add(fav);
            JsonArray jsonArraySave = gson.toJsonTree(mojaLista).getAsJsonArray();
            tinydb = new TinyDB(this);
            tinydb.putString("jsonArray", jsonArraySave.toString());
            btn.setBackground(getResources().getDrawable(R.drawable.favourite_full));
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            Log.d("State", "saved");
        }
    }

}


