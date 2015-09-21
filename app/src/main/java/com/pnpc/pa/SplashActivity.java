package com.pnpc.pa;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by markusmcgee on 9/21/15.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
