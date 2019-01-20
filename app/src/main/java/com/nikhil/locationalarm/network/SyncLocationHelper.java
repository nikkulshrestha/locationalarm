package com.nikhil.locationalarm.network;

import android.content.Context;
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
import com.nikhil.locationalarm.helper.SharedPreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class SyncLocationHelper {

    private static final String TAG = SyncLocationHelper.class.getSimpleName();

    public void syncLocation(Context context, LocationModel location) {
        AppCache.getCache().setCurrentLocation(location);
        saveOfflineLocation(context, location);
        if (AppUtility.isNetworkAvailable(context) && AppCache.getCache().getUserInfo() != null) {
            sendLocationToServer(context, SharedPreferenceManager.getOfflineLocations(context));
        }
    }

    private void saveOfflineLocation(Context context, LocationModel location) {

        SaveLocationRequestModel offlineLocations = SharedPreferenceManager.getOfflineLocations(context);
        offlineLocations.add(location);

        SharedPreferenceManager.saveLocationsIntoPreference(context, offlineLocations);
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
        Gson gson = new Gson();
        Log.d(TAG, "onSaveLocationResponse data: " + gson.toJson(response));

        if (response instanceof RawDataModel) {
            SaveLocationResponseModel locationResponseModel = gson.fromJson(((RawDataModel) response).getRawContent(),
                    SaveLocationResponseModel.class);
            if (locationResponseModel.getStatus().getMessage().equals(Constants.MESSAGE_SUCCESS)) {
                Log.d(TAG, "onSaveLocationResponse: Clear offline locations");
                SharedPreferenceManager.saveLocationsIntoPreference(context, null);
            }
        }
    }
}
