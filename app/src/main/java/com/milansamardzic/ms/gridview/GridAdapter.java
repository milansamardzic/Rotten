package com.milansamardzic.ms.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.milansamardzic.ms.RemoteImageView;
import com.milansamardzic.ms.objects.Movie;
import com.milansamardzic.ms.rottentomatomovie.R;

import java.util.ArrayList;

/**
 * Created by ms on 10/26/14.
 */
public class GridAdapter  extends ArrayAdapter<Movie> {

   // ArrayList<SingleItem> list;
    Context context;

    public GridAdapter(Context context, ArrayList<Movie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
        ViewHolder(View v) {
            poster = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.tvTitleGV);
            year = (TextView) v.findViewById(R.id.bookmarkYear);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        View row = convertView;

        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.single_item_gv, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }


        TextView title = (TextView) row.findViewById(R.id.tvTitleGV);
        title.setText(movie.getTitle() + " (" + movie.getYear() + ")");

        TextView year = (TextView) row.findViewById(R.id.bookmarkYear);
        Integer yearInt = movie.getYear();
        year.setText(yearInt.toString());

        RemoteImageView remoteImageView = (RemoteImageView) row.findViewById(R.id.image);
        String imageURL = fixAPI(movie.getLargePosterUrl());
        remoteImageView.setImageURL(imageURL);

        return row;
 }

    public String fixAPI(String brokenImageLink){
        String imageURL = brokenImageLink;
        return imageURL = imageURL.replaceAll("[//_]+[t]..", "_det");
    }



}