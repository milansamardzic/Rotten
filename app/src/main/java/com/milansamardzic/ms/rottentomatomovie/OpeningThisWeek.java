package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ms on 10/24/14.
 */
public class OpeningThisWeek extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.opening_this_week, container, false);
        return rootView;
    }

}