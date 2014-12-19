package com.milansamardzic.ms.rottentomatomovie;

import android.util.Log;

/**
 * Created by ms on 11/11/14.
 */
public class BoxOffice extends Sve {
    @Override
    public String link() {

        TinyDB db = new TinyDB(getActivity());
        int i = db.getInt("box");
            if(i==0) {
                i = 50;
            }

        Log.d("box", String.valueOf(i));
        String link = "lists/movies/box_office.json?limit=" + String.valueOf(i) + "&country=us";
        return link;
    }
}
