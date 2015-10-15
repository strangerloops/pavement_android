package com.pnpc.pa;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pnpc.pa.event.PavementBusProvider;
import com.pnpc.pa.event.PavementEvent;
import com.pnpc.pa.service.GPSService;
import com.pnpc.pa.utility.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    Button mPavementButton = null;
    LinearLayout main_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_view = (LinearLayout) findViewById(R.id.main_view);
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

    private void stopPavementService() {
        stopService(new Intent(this, GPSService.class));
    }

    private void startPaymentService() {
        startService(new Intent(this, GPSService.class));
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

        if (Utility.getInstance().isServiceRunning(GPSService.class.getName(), this) == false) {
            main_view.setBackgroundColor(getResources().getColor(R.color.pavement_stop_view));
            startPaymentService();
            ((Button) v).setText("STOP");
            ((Button) v).setBackgroundResource(R.drawable.circle_button_shape_stop);
        }
        else {
            main_view.setBackgroundColor(getResources().getColor(R.color.pavement_go_view));
            stopPavementService();
            ((Button) v).setText("START");
            ((Button) v).setBackgroundResource(R.drawable.circle_button_shape_go);

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
