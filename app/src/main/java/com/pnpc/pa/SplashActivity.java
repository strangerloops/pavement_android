package com.pnpc.pa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pnpc.pa.event.PavementBusProvider;


/**
 * Created by markusmcgee on 9/21/15.
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PavementBusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PavementBusProvider.getInstance().register(this);

        //Once Splash Screen is shown and necessary resources requested or loaded then this intent should be launched.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }, 2500L);


    }
}
