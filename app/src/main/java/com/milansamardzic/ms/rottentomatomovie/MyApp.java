package com.milansamardzic.ms.rottentomatomovie;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by ms on 11/12/14.
 */

@ReportsCrashes(
        formKey = ""
  //      mailTo =  "milan.samardzic@gmail.com",
   //     mode = ReportingInteractionMode.SILENT
)


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // ACRA.init(this);
    }
}
