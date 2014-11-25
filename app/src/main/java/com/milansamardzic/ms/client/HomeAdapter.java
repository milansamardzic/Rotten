package com.milansamardzic.ms.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Created by ms on 11/17/14.
 */
public class HomeAdapter  extends ArrayAdapter<Movie> {


    public HomeAdapter(Context context, ArrayList<Movie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_home, null);
        }

        //---title---//
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitleGV);
        tvTitle.setText(movie.getTitle() + " (" + movie.getYear() + ")");

        //---image---//
        RemoteImageView remoteImageView = (RemoteImageView) convertView.findViewById(R.id.image);
        String imageURL = fixAPI(movie.getLargePosterUrl());
        remoteImageView.setImageURL(imageURL);


        return convertView;
    }

    public String fixAPI(String brokenImageLink) {
        String imageURL = brokenImageLink;
        return imageURL = imageURL.replaceAll("[//_]+[t]..", "_det");

    }
}
