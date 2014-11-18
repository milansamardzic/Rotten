package com.milansamardzic.ms.client;

import android.net.http.AndroidHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RottenTomatoesClient {
    private final String API_KEY = "73t2punmezgthuut9hrqvga4";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private AsyncHttpClient client;
    public AndroidHttpClient androidHttpClient;
    public RottenTomatoesClient() {
          //  this.androidHttpClient = new AndroidHttpClient();
            this.client = new AsyncHttpClient();
    }
    // http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=73t2punmezgthuut9hrqvga4
    public void getMovies(JsonHttpResponseHandler handler, String urlAPI) {
        String url = getApiUrl(urlAPI);
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
       // client.get(url, handler);

    }
    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

}