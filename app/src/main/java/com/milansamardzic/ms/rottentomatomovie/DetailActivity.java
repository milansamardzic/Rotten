package com.milansamardzic.ms.rottentomatomovie;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.milansamardzic.ms.RemoteImageView;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.graphics.Color.parseColor;

public class DetailActivity extends ActionBarActivity {
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
    private Movie movie;
    private Movie fav;
    private String str, strOfRecent;
    private Gson gson;
    private TinyDB tinydb;
    public int changeIcon = 0;
    public ArrayList<Movie> mojaLista = new ArrayList<Movie>();
    public ArrayList<Movie> mojaListaOfRecent = new ArrayList<Movie>();
public Movie ppom;
    public Movie ee;
    public int progressStatusAudience = -1;
    public int progressStatusCritics = -1;
    private Handler handler = new Handler();
    int progressStatusAudienceMax = 0;
    int progressStatusCriticsMax = 0;


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
//F79A36
//5DB6C9
        pgCB.getProgressDrawable().setColorFilter(parseColor("#B71C1C"), PorterDuff.Mode.SRC_IN);
        pgAB.getProgressDrawable().setColorFilter(parseColor("#3F51B5"), PorterDuff.Mode.SRC_IN);


        Movie movie = (Movie) getIntent().getSerializableExtra(Sve.MOVIE_DETAIL_KEY);

        Log.d("film", Sve.MOVIE_DETAIL_KEY);

        loadMovie(movie);
    }


    public void checkIsFirstTime() {
        SharedPreferences.Editor firstTimeOnDetail= getPreferences(MODE_PRIVATE).edit();
        SharedPreferences count = getPreferences(MODE_PRIVATE);
        Boolean isIt = count.getBoolean("first", false);
        if (isIt == false) {
            ActionItemTarget target = new ActionItemTarget(this, R.id.action_settings);
            ShowcaseView sv = new ShowcaseView.Builder(this)
                    .setTarget(target)
                    .setContentTitle("Favourite")
                    .setContentText("Select star to add or remove movie from your favourites")
                    .doNotBlockTouches()
                    .build();
            firstTimeOnDetail.putBoolean("first", true);
            firstTimeOnDetail.apply();
        }
    }

    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {
        ee = movie;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setTitle(movie.getTitle());
        }
        tvTitle.setText(movie.getTitle());


        //pgAB.setProgress(movie.getAudienceScore());
        progressStatusAudienceMax = movie.getAudienceScore();
        progressStatusCriticsMax = movie.getCriticsScore();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatusAudience < progressStatusAudienceMax || progressStatusCritics  < progressStatusCriticsMax) {

                    if(progressStatusAudience<progressStatusAudienceMax){
                     progressStatusAudience += 1;}

                    if(progressStatusCritics<progressStatusCriticsMax){
                      progressStatusCritics +=1;}

                    handler.post(new Runnable() {
                        public void run() {
                            pgAB.setProgress(progressStatusAudience);
                            pgCB.setProgress(progressStatusCritics);
                            tvAudienceScore.setText(progressStatusAudience + "%");
                            tvCriticsScore.setText(progressStatusCritics + "%");
                        }
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



    //   pgAB.setProgress(movie.getAudienceScore());
    //    pgCB.setProgress(movie.getCriticsScore());
        String year = String.valueOf(movie.getYear());

        if (movie.getCriticsScore() >= 75) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_fresh));
        } else if (movie.getCriticsScore() < 50) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_bad));
        } else if (movie.getCriticsScore() >= 50 && movie.getCriticsScore() < 75) {
            ivCritichScore.setImageDrawable(getResources().getDrawable(R.drawable.c_img_god));
        }



        String dateString = movie.getRelaseDate().toString();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yy");
    //    tvYear.setText(fmtOut.format(date).toString());

        tvYear.setText(year + " | " + movie.getDuration() + " min | " + movie.getMpaaRating());

        // String criticsScore = String.valueOf(movie.getCriticsScore());
        // String audianceScore = String.valueOf(movie.getAudienceScore());
        // tvAudienceScore.setText(audianceScore + "%");
        // tvCriticsScore.setText(criticsScore + "%");

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
                changeIcon = 1;
              //  btn.setBackground(getResources().getDrawable(R.drawable.favourite_full));
                Log.d("State", "true");
            }
        }

    }

    public String fixAPI(String brokenImageLink) {
        String imageURL = brokenImageLink;
        return imageURL = imageURL.replaceAll("[//_]+[t]..", "_det"); //_org
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_activity_menu, menu);
        checkIsFirstTime();
        if (changeIcon==1){
        menu.findItem(R.id.action_settings).setIcon(R.drawable.favourite_full); }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()) {
            case R.id.action_settings:
           //     item.setIcon(R.drawable.favourite_full);
            {
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
                                item.setIcon(R.drawable.favourite_empty);
                              //  btn.setBackground(getResources().getDrawable(R.drawable.favourite_empty));
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
                            item.setIcon(R.drawable.favourite_full);
                            //btn.setBackground(getResources().getDrawable(R.drawable.favourite_full));
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                            Log.d("State", "saved");
                        }
                }

                break;
            case android.R.id.home:
                    super.onBackPressed();
                    break;
            }
        return super.onOptionsItemSelected(item);
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

}


