package com.nikhil.locationalarm.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationListener;
import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.utils.ILocationPermissionSuccessListener;
import com.nikhil.locationalarm.utils.LocationPermissionHelper;
import com.nikhil.locationalarm.utils.AppLocationService;

public class AlarmListActivity extends AppCompatActivity implements ILocationPermissionSuccessListener, LocationListener {

    private LocationPermissionHelper mLocationPermissionHelper;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String TAG = AlarmListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        mLocationPermissionHelper = new LocationPermissionHelper();
        mLocationPermissionHelper.checkForPermissions(this);
        startLocationService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }


    private void checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                Toast.makeText(this, "Play service not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationPermissionSuccess() {
        Log.d(TAG, "onLocationPermissionSuccess: launching service");
        startLocationService();
    }

    private void startLocationService() {
        ActivityManager.RunningServiceInfo runningService = getRunningServiceInfo(AppLocationService.class
                , this);
        if (runningService != null) {
            stopService(new Intent(this, runningService.getClass()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, AppLocationService.class));
        } else {
            startService(new Intent(this, AppLocationService.class));
        }
    }

    public static ActivityManager.RunningServiceInfo getRunningServiceInfo(Class serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return service;
            }
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
