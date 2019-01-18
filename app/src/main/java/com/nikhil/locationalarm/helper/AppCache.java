package com.nikhil.locationalarm.helper;

import com.nikhil.locationalarm.model.UserInfo;

public class AppCache {

    private static AppCache mCache = new AppCache();
    private UserInfo mUserInfo;

    private AppCache() {

    }

    public static AppCache getCache() {
        return mCache;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = mUserInfo;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
