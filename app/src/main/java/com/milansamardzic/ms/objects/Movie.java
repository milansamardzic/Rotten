package com.milansamardzic.ms.objects;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private int year;
    private String synopsis;
    private String posterUrl;
    private String largePosterUrl;
    private int audienceScore;
    private String relaseDate;
    private int duration;
    private int criticsScore;
    private String mpaaRating;
    private ArrayList<String> castList;

    public String getId() {
        return id;
    }

    private String id;

    public String getRelaseDate() {
        return relaseDate;
    }

    public static Movie fromJsonn(JSONObject jsonObject){
        Movie m = new Movie();
        try {
            m.id = jsonObject.getString("id");
            m.title = jsonObject.getString("title");
            m.year = jsonObject.getInt("year");
            m.synopsis = jsonObject.getString("synopsis");
            m.criticsScore = jsonObject.getJSONObject("ratings").getInt("critics_score");
            m.audienceScore = jsonObject.getJSONObject("ratings").getInt("audience_score");
            m.largePosterUrl = jsonObject.getJSONObject("posters").getString("detailed");
            m.relaseDate = jsonObject.getJSONObject("release_dates").getString("theater");
            m.duration= jsonObject.getInt("runtime");
            m.mpaaRating = jsonObject.getString("mpaa_rating");
            m.castList = new ArrayList<String>();
            JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                m.castList.add(abridgedCast.getJSONObject(i).getString("name"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return m;
    }


    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        ArrayList<Movie> businesses = new ArrayList<Movie>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject newJsonObj = null;
            try {
                newJsonObj = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            //---padne---//
            Movie movJSON = Movie.fromJsonn(newJsonObj);
//           Log.d("rezultatMovie", newJsonObj.getTitle());
            //---padne up---/

            if (movJSON != null) {
                businesses.add(movJSON);
            }
        }
        return businesses;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public String getCastList() {
       return TextUtils.join(", ", castList);
    }

    public int getDuration() {
        return duration;
    }

    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    /*public String getCriticsConsensus() {
        return criticsConsensus;
    }*/

    public int getAudienceScore() {
        return audienceScore;
    }

    public void populateFrom(JSONObject jsonObject)
    {
        try {
            this.id = jsonObject.getString("id");
            this.title = jsonObject.getString("title");
            this.year = jsonObject.getInt("year");
            this.duration = jsonObject.getInt("duration");
            this.criticsScore = jsonObject.getInt("criticsScore");
            this.audienceScore = jsonObject.getInt("audienceScore");
            this.synopsis = jsonObject.getString("synopsis");
            this.largePosterUrl = jsonObject.getString("largePosterUrl");
            this.relaseDate = jsonObject.getString("relaseDate");
            this.mpaaRating = jsonObject.getString("mpaaRating");

            this.castList = new ArrayList<String>();
            this.castList.add((String.valueOf(jsonObject.getJSONArray("castList")).replace("[\"", "").replace("\"]","").replace("\""," ")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


/* depricated
    public void populateFromRecent(JSONObject jsonObject1)
    {
        try {
            this.title = jsonObject1.getString("title");
            this.year = jsonObject1.getInt("year");
            this.largePosterUrl = jsonObject1.getString("largePosterUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

*/

}
