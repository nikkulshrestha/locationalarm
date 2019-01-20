package com.nikhil.locationalarm.model;

/**
 * "locationId": 1,
 * "latitude": "23.67",
 * "longitude": "87.90",
 * "userId": {
 * "userId": 1370,
 * "name": "INTERVIEWERON",
 * "mobile": "3403243489",
 * "role": "INTERVIEWER"
 */
public class LocationHistoryItem {
    private int locationId;
    private String latitude;
    private String longitude;
    private UserInfo userId;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
        this.userId = userId;
    }
}