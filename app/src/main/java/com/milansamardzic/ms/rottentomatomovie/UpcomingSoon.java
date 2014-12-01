package com.milansamardzic.ms.rottentomatomovie;

/**
 * Created by ms on 11/11/14.
 */
public class UpcomingSoon extends Sve {
    @Override
    public String link() {

        TinyDB db = new TinyDB(getActivity());
        int i = db.getInt("upcoming");
        if(i==0) {
            i = 50;
        }

        //String upcomingLimit = "50";
        String link = "lists/movies/upcoming.json?page_limit=" + String.valueOf(i) +  "&page=1";
        return link;
    }
}
