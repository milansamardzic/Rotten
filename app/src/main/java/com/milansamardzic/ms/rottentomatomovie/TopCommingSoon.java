package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by ms on 10/24/14.
 */
public class TopCommingSoon extends Fragment implements
        View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.top_comming_soon, container, false);


        Button b = (Button)  rootView.findViewById(R.id.button);
        b.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                poruka();
                break;
        }


    }

    public void poruka(){
        Toast.makeText(getActivity(), "lalala", Toast.LENGTH_LONG).show();
    }

}
