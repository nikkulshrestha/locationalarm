package com.nikhil.locationalarm.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nikhil.locationalarm.model.AlarmItem;
import com.nikhil.locationalarm.model.SaveLocationRequestModel;
import com.nikhil.locationalarm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceManager {


    public static SaveLocationRequestModel getOfflineLocations(Context context) {
        SaveLocationRequestModel offlineLocations = new SaveLocationRequestModel();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(Constants.PREF_OFFLINE_LOCATIONS_KEY)) {
            String offlineLocationsJson = preferences.getString(Constants.PREF_OFFLINE_LOCATIONS_KEY,
                    null);
            offlineLocations = new Gson().fromJson(offlineLocationsJson, SaveLocationRequestModel.class);
        }
        return offlineLocations;
    }

    public static void saveLocationsIntoPreference(Context context, SaveLocationRequestModel offlineLocations) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (offlineLocations == null) {
            editor.remove(Constants.PREF_OFFLINE_LOCATIONS_KEY);
        } else {
            editor.putString(Constants.PREF_OFFLINE_LOCATIONS_KEY, new Gson().toJson(offlineLocations));
        }
        editor.commit();
    }

    public static ArrayList<AlarmItem> getAlarmList(Context context) {
        ArrayList<AlarmItem> alarmItemList = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(Constants.PREF_ALARM_ITEMS_KEY)) {
            String offlineLocationsJson = preferences.getString(Constants.PREF_ALARM_ITEMS_KEY,
                    null);
            alarmItemList = new Gson().fromJson(offlineLocationsJson, new TypeToken<ArrayList<AlarmItem>>(){}.getType());
        }
        return alarmItemList;
    }

    public static void saveAlarmListIntoPreference(Context context, ArrayList<AlarmItem> alarmItemList) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (alarmItemList == null) {
            editor.remove(Constants.PREF_ALARM_ITEMS_KEY);
        } else {
            editor.putString(Constants.PREF_ALARM_ITEMS_KEY, new Gson().toJson(alarmItemList));
        }
        editor.commit();
    }
}
