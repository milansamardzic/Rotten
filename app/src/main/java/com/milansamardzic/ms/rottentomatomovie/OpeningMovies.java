package com.milansamardzic.ms.rottentomatomovie;

import android.view.View;

/**
 * Created by ms on 11/11/14.
 */
public class OpeningMovies extends Sve {
    @Override
    public String link() {
        String numberOfOpeningMovies = "50";
        String link ="lists/movies/opening.json?limit=" + numberOfOpeningMovies + "&country=us";
        return link;
    }
}
