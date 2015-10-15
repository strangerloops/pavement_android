package com.pnpc.pa.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.pnpc.pa.R;

/**
 * Created by markusmcgee on 9/22/15.
 */
public class GPSService extends Service implements LocationListener, SensorEventListener {

    private static final String TAG = "GPSService";
    public static final String NAME = "GPSService";
    private LocationManager mLocationManager;
    private Location mLocation;
    private NotificationManager mNotificationManager = null;

    private static final int NOTIFICATION = 1;


    private SensorManager mSensorManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service Started");

        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.bike_with_speed_lines_small)
                        .setContentTitle("Pavement App Running")
                        .setContentText("GPS and Accelerometer readings being taken.");

        Notification mNotification = mBuilder.build();
        mNotification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(NOTIFICATION, mNotification);

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        mLocationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return START_NOT_STICKY;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, this);


        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            mLocationManager.removeUpdates(this);
            mSensorManager.unregisterListener(this);
            mNotificationManager.cancel(NOTIFICATION);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        Log.d(TAG, mLocation.toString());

        new LocationChangeTask().execute(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float z = values[2];

        Log.d(TAG + "Z: ", Float.toString(z));

        //new SensorEventTask().execute(event);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged");
    }

    private class LocationChangeTask extends AsyncTask<Location, Void, Void> {
        @Override
        protected Void doInBackground(Location... params) {
            return null;
        }
    }

    private class SensorEventTask extends AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... params) {
            return null;
        }
    }

}
