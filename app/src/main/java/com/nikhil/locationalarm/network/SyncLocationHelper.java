package com.nikhil.locationalarm.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nikhil.locationalarm.model.LocationModel;
import com.nikhil.locationalarm.model.NetworkModel;
import com.nikhil.locationalarm.model.RawDataModel;
import com.nikhil.locationalarm.model.SaveLocationRequestModel;
import com.nikhil.locationalarm.model.SaveLocationResponseModel;
import com.nikhil.locationalarm.utils.AppCache;
import com.nikhil.locationalarm.utils.AppUtility;
import com.nikhil.locationalarm.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SyncLocationHelper {

    private static final String TAG = SyncLocationHelper.class.getSimpleName();

    public void syncLocation(Context context, LocationModel location) {
        saveOfflineLocation(context, location);
        if (AppUtility.isNetworkAvailable(context)) {
            sendLocationToServer(context, getOfflineLocations(context));
        }
    }

    private void saveOfflineLocation(Context context, LocationModel location) {

        SaveLocationRequestModel offlineLocations = getOfflineLocations(context);
        offlineLocations.add(location);

        saveLocationsIntoPreference(context, offlineLocations);
    }

    private void sendLocationToServer(Context context, SaveLocationRequestModel offlineLocations) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + AppCache.getCache().getUserInfo().getToken());

        NetworkRequest request = new NetworkRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.ENDPOINT_SAVE_LOCATION,
                response -> onSaveLocationResponse(context, response),
                this::onSaveLocationError,
                new RawDataModel(),
                headers,
                new Gson().toJson(offlineLocations));

        Volley.newRequestQueue(context).add(request);
    }

    private void onSaveLocationError(VolleyError volleyError) {
        Log.d(TAG, "onSaveLocationError: " + new Gson().toJson(volleyError));

    }

    private void onSaveLocationResponse(Context context, NetworkModel response) {
        Log.d(TAG, "onSaveLocationResponse: " + new Gson().toJson(response));
        if (response instanceof SaveLocationResponseModel && ((SaveLocationResponseModel) response).getStatus()
                .getMessage().equals(Constants.MESSAGE_SUCCESS)) {
            saveLocationsIntoPreference(context, null);
        }
    }

    private SaveLocationRequestModel getOfflineLocations(Context context) {
        SaveLocationRequestModel offlineLocations = new SaveLocationRequestModel();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(Constants.PREF_OFFLINE_LOCATIONS_KEY)) {
            String offlineLocationsJson = preferences.getString(Constants.PREF_OFFLINE_LOCATIONS_KEY,
                    null);
            offlineLocations = new Gson().fromJson(offlineLocationsJson, SaveLocationRequestModel.class);
        }
        return offlineLocations;
    }

    private void saveLocationsIntoPreference(Context context, SaveLocationRequestModel offlineLocations) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (offlineLocations == null) {
            editor.remove(Constants.PREF_OFFLINE_LOCATIONS_KEY);
        } else {
            editor.putString(Constants.PREF_OFFLINE_LOCATIONS_KEY, new Gson().toJson(offlineLocations));
        }
        editor.commit();
    }
}
