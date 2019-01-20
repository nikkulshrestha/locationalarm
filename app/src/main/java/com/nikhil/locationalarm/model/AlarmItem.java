package com.nikhil.locationalarm.model;

public class AlarmItem {
    private String alarmName;
    private boolean isActive;
    private LocationModel triggerLocation;

    public AlarmItem(String alarmName, boolean isActive, LocationModel triggerLocation) {
        this.alarmName = alarmName;
        this.isActive = isActive;
        this.triggerLocation = triggerLocation;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocationModel getTriggerLocation() {
        return triggerLocation;
    }

    public void setTriggerLocation(LocationModel triggerLocation) {
        this.triggerLocation = triggerLocation;
    }
}
