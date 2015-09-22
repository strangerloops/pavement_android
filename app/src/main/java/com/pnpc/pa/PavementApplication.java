package com.pnpc.pa;

import android.app.Application;
import android.content.Intent;

import com.pnpc.pa.service.GPSService;

/**
 * Created by markusmcgee on 9/22/15.
 */
public class PavementApplication extends Application {

    Intent serviceIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //serviceIntent = new Intent(this, GPSService.class);
        //startService(serviceIntent);

    }

    @Override
    public void onTerminate() {

        //if(serviceIntent != null)
        //    stopService(serviceIntent);

        super.onTerminate();
    }

    /*
    @Override
    public void onLowMemory() {

        if(serviceIntent != null)
            stopService(serviceIntent);

        super.onLowMemory();
    }
    */

}
