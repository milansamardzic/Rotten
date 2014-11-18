package com.milansamardzic.ms.rottentomatomovie;

/**
 * Created by ms on 11/11/14.
 */
public class UpcomingSoon extends Sve {
    @Override
    public String link() {
        String upcomingLimit = "50";
        String link = "lists/movies/upcoming.json?page_limit=" + upcomingLimit +  "&page=1";
        return link;
    }
}
