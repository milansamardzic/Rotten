package com.milansamardzic.ms.rottentomatomovie;

/**
 * Created by ms on 11/11/14.
 */
public class BoxOffice extends Sve {
    @Override
    public String link() {
        String numberOfMovie="50";
        String link = "lists/movies/box_office.json?limit=" + numberOfMovie + "&country=us";
        return link;
    }
}
