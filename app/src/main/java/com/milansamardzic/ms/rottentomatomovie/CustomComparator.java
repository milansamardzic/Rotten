package com.milansamardzic.ms.rottentomatomovie;

import com.milansamardzic.ms.objects.Movie;

/**
 * Created by ms on 11/26/14.
 */
public class CustomComparator implements java.util.Comparator<Movie> {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compare(Movie lhs, Movie rhs) {

        return lhs.getRelaseDate().compareTo(rhs.getRelaseDate());
        //return String.valueOf(lhs.getDuration()).compareTo(String.valueOf(rhs.getDuration()));
    }

}

