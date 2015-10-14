package com.pnpc.pa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pnpc.pa.service.GPSService;
import com.pnpc.pa.utility.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Intent gpsIntent;
    Button mPavementButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPavementButton = (Button) findViewById(R.id.start_stop_btn);
        mPavementButton.setOnClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (((Button) v).getText().equals("START")) {
            if (Utility.getInstance().isServiceRunning(GPSService.NAME, getApplicationContext()) == false) {
                gpsIntent = new Intent(getApplicationContext(), GPSService.class);
                startService(gpsIntent);
                ((Button) v).setText("STOP");
            }
        }
        else {
            if (Utility.getInstance().isServiceRunning(GPSService.NAME, getApplicationContext()) == true) {
                if (gpsIntent != null) ;
                stopService(gpsIntent);
                ((Button) v).setText("START");

                        /*

                        This should post on the first go with no endtime.  Use the rideId returned for the readings. Then at the end of the ride
                        send the rideid.

                        POST https://project-pavement.herokuapp.com/rides
                        {
                           ride:{
                                start_time: UNIX NO,
                                device_id: UUID?,  //Will show how to generate.  Unique to device, won't duplicate and consistant.
                                end_time: UNIX NO
                           }
                        }

                        On every single GPS update post your reading.
                        POST https://project-pavement.herokuapp.com/readings
                        {
                            reading:{
                                startLat:
                                startLon:
                                endLat:
                                endLon:
                                accelerationX:[array of longs]
                                accelerationY:[array of longs]
                                accelerationZ:[array of longs]
                                accelerationG:[array of longs]
                                startTime:
                                endTime:
                                rideId: Returned from Ride above
                            ]

                        }

                         */
            }
        }
    }



}
