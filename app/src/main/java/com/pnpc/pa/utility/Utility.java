package com.pnpc.pa.utility;

/**
 * Created by markusmcgee on 9/10/15.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by markusmcgee on 4/16/15.
 */
public class Utility {

    private static final Utility INSTANCE = new Utility();
    private static final String TAG = "Utility";

    private static Context mContext;

    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;
    private static final int REQUEST_COURSE_LOCATION = 1;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation = null;


    private Utility() {
    }

    public static Utility getInstance() {
        return INSTANCE;
    }

    //Pass context in if context is needed to complete procedure.
    public static Utility getInstance(Context context) {
        mContext = context;
        return INSTANCE;
    }



    public GoogleApiClient buildGoogleApiClient(GoogleApiClient.ConnectionCallbacks connectionCallback, GoogleApiClient.OnConnectionFailedListener failedCallback) {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(connectionCallback)
                    .addOnConnectionFailedListener(failedCallback)
                    .addApi(LocationServices.API)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mGoogleApiClient;

    }


    public boolean isNetworkAvailable(Context context) {
        mContext = context;
        boolean mIsNetworkAvailable = false;
        mIsNetworkAvailable = isNetworkAvailable();
        if (!mIsNetworkAvailable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("No Internet");
            builder.setMessage("Please check your internet connection and try again.");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return mIsNetworkAvailable;

    }

    public boolean isNetworkAvailable() {
        if (mContext != null) {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // if no network is available networkInfo will be null
            // otherwise check if we are connected
            return networkInfo != null && networkInfo.isConnected();
        }
        else {
            return false;
        }
    }

    public void setLastLocation(Location lastLocation) {
        mLastLocation = lastLocation;
    }

    public Location getLastLocation() {
        return mLastLocation;
    }


}
