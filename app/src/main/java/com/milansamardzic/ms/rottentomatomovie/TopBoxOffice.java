package com.milansamardzic.ms.rottentomatomovie;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ms on 10/24/14.
 */
public class TopBoxOffice extends Fragment implements AdapterView.OnItemClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.top_box_office, container, false);


        GridView gwTBO = (GridView) rootView.findViewById(R.id.gvtbo);
        gwTBO.setAdapter(new GridAdapter(getActivity()));
        gwTBO.setOnItemClickListener(this);

    return rootView;
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position ==0) {
            Toast.makeText(getActivity(), "poruka", Toast.LENGTH_LONG);
        }
        if (position == 1){
            Toast.makeText(getActivity(), "poruka-1", Toast.LENGTH_LONG);
        }
    }

    class SingleItem {
    int imageID;
    String countryName;

    SingleItem(int imageID, String countryName) {
        this.imageID = imageID;
        this.countryName = countryName;
    }
}

class GridAdapter extends BaseAdapter {

    ArrayList<SingleItem> list;
    Context context;

    GridAdapter(Context context) {
        list = new ArrayList<SingleItem>();
        this.context = context;
        String[] tempCountryNames = context.getResources().getStringArray(R.array.country);

        int[] countryImages = {R.drawable.p1,
                R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8
               };

        for (int i = 0; i < 8; i++) {
            SingleItem tempItem = new SingleItem(countryImages[i], tempCountryNames[i]);
            list.add(tempItem);
        }

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView myCountry;
        TextView countryName;
        ViewHolder(View v) {
            myCountry = (ImageView) v.findViewById(R.id.imageView);
            countryName = (TextView) v.findViewById(R.id.textView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_item_gv, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }

        SingleItem temp = list.get(position);
        holder.myCountry.setImageResource(temp.imageID);
        holder.countryName.setText(temp.countryName);
        return row;
    }
}

}
