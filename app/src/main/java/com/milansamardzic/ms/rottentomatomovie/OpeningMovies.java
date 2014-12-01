package com.milansamardzic.ms.rottentomatomovie;

import android.view.View;

/**
 * Created by ms on 11/11/14.
 */
public class OpeningMovies extends Sve {
    @Override
    public String link() {

        TinyDB db = new TinyDB(getActivity());
        int i = db.getInt("opening");
        if(i==0) {
            i = 50;
        }

      //  String numberOfOpeningMovies = "50";
        String link ="lists/movies/opening.json?limit=" + String.valueOf(i) +  "&country=us";
        return link;
    }
}
