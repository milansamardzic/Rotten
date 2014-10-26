package com.milansamardzic.ms.navigationdrawer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.milansamardzic.ms.rottentomatomovie.R;

/**
 * Created by ms on 10/25/14.
*/

public class CustomNavDraw extends BaseAdapter {

    private Context context;
    public String[] opt;
    int[] images = {R.drawable.box_office,
            R.drawable.comming_soon,
            R.drawable.opening_movies,
            R.drawable.in_theaters};


    public CustomNavDraw(Context context){
        this.context = context;
        opt = context.getResources().getStringArray(R.array.items);
    }
    @Override
    public int getCount() {

        return opt.length;
    }

    @Override
    public Object getItem(int position) {
        return opt[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;

        if(convertView == null){
             LayoutInflater inflater = (LayoutInflater) context
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.custom_nav_draw, parent, false);
        }else{
             row=convertView;
        }

       ImageView listImageView = (ImageView) row.findViewById(R.id.imageView1);
       TextView listTextView = (TextView) row.findViewById(R.id.textView1);

        listImageView.setImageResource(images[position]);
        listTextView.setText(opt[position]);
         return row;
    }
}
