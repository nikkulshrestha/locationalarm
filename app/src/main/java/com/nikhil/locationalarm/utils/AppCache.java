package com.nikhil.locationalarm.utils;

import com.nikhil.locationalarm.model.LocationModel;
import com.nikhil.locationalarm.model.UserInfo;

import java.util.List;

public class AppCache {

    private static AppCache mCache = new AppCache();
    private UserInfo mUserInfo;
    private LocationModel mCurrentLocation;

    private AppCache() {

    }

    public static AppCache getCache() {
        return mCache;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public LocationModel getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setCurrentLocation(LocationModel currentLocation) {
        this.mCurrentLocation = currentLocation;
    }
}
