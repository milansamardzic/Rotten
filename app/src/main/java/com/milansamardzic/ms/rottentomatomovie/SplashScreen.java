package com.milansamardzic.ms.rottentomatomovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ms on 10/26/14.
 */
public class SplashScreen extends Activity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    public TextView tvLoad;
    public int i=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        ImageView animationTarget = (ImageView) findViewById(R.id.splashscreen);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point1);
        animationTarget.startAnimation(animation);

        tvLoad =(TextView) findViewById(R.id.tvLoad);

        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {

                    i +=1;

                    Handler handler = null;
                    handler.post(new Runnable() {
                        public void run() {
                            tvLoad.setText(i + "%");
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();




        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
