package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by ms on 11/16/14.
 */
public class About extends Fragment implements View.OnClickListener{
    int click=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.about, container, false);


        ImageView imgV = (ImageView) rootView.findViewById(R.id.splashscreen);
        imgV.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        click++;
        if(click>=7) {
            ImageView animationTarget = (ImageView) getActivity().findViewById(R.id.splashscreen);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_around_center_point1);
            animationTarget.startAnimation(animation);
        }
    }

}
