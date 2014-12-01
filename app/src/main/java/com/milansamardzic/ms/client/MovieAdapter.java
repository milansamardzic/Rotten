package com.milansamardzic.ms.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.milansamardzic.ms.RemoteImageView;
import com.milansamardzic.ms.objects.Movie;
import com.milansamardzic.ms.rottentomatomovie.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ms on 11/27/14.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    String where;
    ArrayList<Movie> movies = null;

    public MovieAdapter(Context context, ArrayList<Movie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_card_list_view, null);
        }

        //---title---//
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getTitle());

        //---year---//
        TextView tvYearTitle = (TextView) convertView.findViewById(R.id.tvYearTitle);
        tvYearTitle.setText(" (" + movie.getYear() + ")");

        //---relase date---//
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        // tvDate.setText(movie.getRelaseDate());
        String dateString = movie.getRelaseDate().toString();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("MMMM-dd");
        tvDate.setText(fmtOut.format(date).toString());

        //---runtime---//
        TextView mr = (TextView) convertView.findViewById(R.id.mocni_rendzer);
        mr.setText(movie.getDuration() + " min");

        //---Critics score---//
        RatingBar rb= (RatingBar) convertView.findViewById(R.id.ratingbar1);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        rb.setRating((movie.getCriticsScore()*5)/100);

        //---image---//
        RemoteImageView remoteImageView = (RemoteImageView) convertView.findViewById(R.id.image);
        String imageURL = fixAPI(movie.getLargePosterUrl());
        remoteImageView.setImageURL(imageURL);


        return convertView;    }

    public String fixAPI(String brokenImageLink){
        String imageURL = brokenImageLink;
        return imageURL = imageURL.replaceAll("[//_]+[t]..", "_det");
    }



}
