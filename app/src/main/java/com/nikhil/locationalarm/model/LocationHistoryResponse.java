package com.nikhil.locationalarm.model;

import java.util.ArrayList;

/**
 * {
 * "status": {
 * "code": 200,
 * "message": "Success"
 * },
 * "result": [
 * {
 * "locationId": 1,
 * "latitude": "23.67",
 * "longitude": "87.90",
 * "userId": {
 * "userId": 1370,
 * "name": "INTERVIEWERON",
 * "mobile": "3403243489",
 * "role": "INTERVIEWER"
 * }
 * },
 * {
 * "locationId": 2,
 * "latitude": "23.69",
 * "longitude": "87.98",
 * "userId": {
 * "userId": 1370,
 * "name": "INTERVIEWERON",
 * "mobile": "3403243489",
 * "role": "INTERVIEWER"
 * }
 * }
 * ]
 * }
 */
public class LocationHistoryResponse extends ResponseModel {
    private ArrayList<LocationHistoryItem> result;

    public ArrayList<LocationHistoryItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<LocationHistoryItem> result) {
        this.result = result;
    }
}
