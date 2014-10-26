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
import com.milansamardzic.ms.gridview.GridAdapter;
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
}
