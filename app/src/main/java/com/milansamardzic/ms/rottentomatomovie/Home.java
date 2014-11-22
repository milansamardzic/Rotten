package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.google.gson.Gson;
import com.milansamardzic.ms.client.HomeAdapter;
import com.milansamardzic.ms.client.MoviesAdapter;
import com.milansamardzic.ms.objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.milansamardzic.ms.rottentomatomovie.R.layout.movie_list;


/**
 * Created by ms on 11/13/14.
 */
public class Home extends Fragment implements View.OnClickListener{
    private static final int MODE_PRIVATE = 1;
    int click=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_frg, container, false);


         checkIsFirstTime();

        Toast.makeText(getActivity(), "Hello, this is first alpha version!", Toast.LENGTH_SHORT).show();


        ImageView imgV = (ImageView) rootView.findViewById(R.id.splashscreen);
        imgV.setOnClickListener(this);

        return rootView;
    }

    public void checkIsFirstTime() {
        SharedPreferences.Editor firstTimeOnHome = this.getActivity().getPreferences(MODE_PRIVATE).edit();
        SharedPreferences count = this.getActivity().getPreferences(MODE_PRIVATE);
        Boolean isIt = count.getBoolean("first", false);
        if (isIt == false) {
            new ShowcaseView.Builder(getActivity())
                    .setTarget(new ActionViewTarget(getActivity(), ActionViewTarget.Type.HOME))
                    .setContentTitle("Menu view")
                    .setContentText("Here is menu")
                    .hideOnTouchOutside()
                    .build();
            firstTimeOnHome.putBoolean("first", true);
            firstTimeOnHome.apply();
        }
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
