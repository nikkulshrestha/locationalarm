package com.nikhil.locationalarm.model;

/**
 * {
 * "status": {
 * "code": 200,
 * "message": "Success"
 * },
 * "result": "Location successfully saved!!"
 * }
 */
public class SaveLocationResponseModel extends ResponseModel {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
