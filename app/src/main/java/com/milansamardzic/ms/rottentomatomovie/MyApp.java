package com.milansamardzic.ms.rottentomatomovie;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by ms on 11/12/14.
 */

@ReportsCrashes(
        formKey = "",
        mailTo =  "milan.samardzic@gmail.com, igor.trncic@atlantbh.com, jasmin.velic@atlantbh.com",
        mode = ReportingInteractionMode.DIALOG
)


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        if (!BuildConfig.DEBUG)
             ACRA.init(this);
    }
}
