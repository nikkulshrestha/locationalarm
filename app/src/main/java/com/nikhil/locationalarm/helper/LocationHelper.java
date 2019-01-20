package com.nikhil.locationalarm.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nikhil.locationalarm.service.AppLocationService;

public class LocationHelper {

    private static final String TAG = LocationHelper.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationPermissionHelper mLocationPermissionHelper;

    public void init(Activity activity) {
        mLocationPermissionHelper = new LocationPermissionHelper();
        mLocationPermissionHelper.checkForPermissions(activity);
        startLocationService(activity);
    }

    private void startLocationService(Activity activity) {
        ActivityManager.RunningServiceInfo runningService = getRunningServiceInfo(AppLocationService.class
                , activity);
        if (runningService != null) {
            activity.stopService(new Intent(activity, runningService.getClass()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(new Intent(activity, AppLocationService.class));
        } else {
            activity.startService(new Intent(activity, AppLocationService.class));
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mLocationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResume(Activity activity) {
        checkPlayServices(activity);

    }

    private void checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                Toast.makeText(activity, "Play service not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onLocationPermissionSuccess(Activity activity) {
        Log.d(TAG, "onLocationPermissionSuccess: launching service");
        startLocationService(activity);
    }
}
