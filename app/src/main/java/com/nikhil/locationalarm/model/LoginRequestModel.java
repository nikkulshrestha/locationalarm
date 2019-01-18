package com.nikhil.locationalarm.model;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class LoginRequestModel extends NetworkModel {
    private String mobile;
    private String password;


    public LoginRequestModel(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
