package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ms on 11/27/14.
 */
public class Settings extends Fragment {
    public TinyDB tdbSettings;
    public TextView tv;
    SeekBar sbBO;
    public TextView tvUpcoming;
    SeekBar upcomingSB;
    public TextView tvOpening;
    SeekBar openingSB;
    public TextView tvTheather;
    SeekBar theatherSB;
    Switch balkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.settings, container, false);
        tdbSettings = new TinyDB(getActivity());

        //---boxoffice---//
        sbBO = (SeekBar) rootView.findViewById(R.id.seekBar);
        tv = (TextView) rootView.findViewById(R.id.textView);

        //---upcoming---//
        upcomingSB = (SeekBar) rootView.findViewById(R.id.upcomingSB);
        tvUpcoming = (TextView) rootView.findViewById(R.id.tvUpcoming);

        //---opening---//
        openingSB = (SeekBar) rootView.findViewById(R.id.openingSB);
        tvOpening = (TextView) rootView.findViewById(R.id.tvOpening);

        //---opening---//
        theatherSB = (SeekBar) rootView.findViewById(R.id.theatherSB);
        tvTheather = (TextView) rootView.findViewById(R.id.tvTheather);

        //---balkan---//
        balkan = (Switch) rootView.findViewById(R.id.switch1);


        boxOfficeSettings();
        upcomingSettings();
        openingSettings();
        teatherSettings();
        balkanSettings();






        return rootView;
    }

    private void balkanSettings() {
        Boolean isMilje = tdbSettings.getBoolean("balkan");
        if(isMilje==true){ balkan.setChecked(true); } else{ balkan.setChecked(false); }

        balkan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    tdbSettings.putBoolean("balkan", true);
                }else{
                    tdbSettings.putBoolean("balkan", false);
                }

            }
        });
    }

    private void teatherSettings() {
        theatherSB.setProgress(tdbSettings.getInt("theather"));
        tvTheather.setText( theatherSB.getProgress() + "/" +  theatherSB.getMax());
        theatherSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvTheather.setText(progress + "/" + seekBar.getMax());
                int theatherSB = progress;
                tdbSettings.putInt("theather", theatherSB);
            }
        });
    }

    private void openingSettings() {
        openingSB.setProgress(tdbSettings.getInt("opening"));
        tvOpening.setText(openingSB.getProgress() + "/" + openingSB.getMax());
        openingSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvOpening.setText(progress + "/" + seekBar.getMax());
                int openingSB = progress;
                tdbSettings.putInt("opening", openingSB);
            }
        });
    }

    private void upcomingSettings() {
        upcomingSB.setProgress(tdbSettings.getInt("upcoming"));
        tvUpcoming.setText(upcomingSB.getProgress() + "/" + upcomingSB.getMax());
        upcomingSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvUpcoming.setText(progress + "/" + seekBar.getMax());
                int upcomingSB = progress;
                tdbSettings.putInt("upcoming", upcomingSB);
            }
        });
    }

    public void boxOfficeSettings(){
        sbBO.setProgress(tdbSettings.getInt("box"));
        tv.setText(sbBO.getProgress() + "/" + sbBO.getMax());
        sbBO.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv.setText(progress + "/" + seekBar.getMax());
                int sizeBO = progress;
                tdbSettings.putInt("box", sizeBO);
            }
        });

    }


}


