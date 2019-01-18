package com.nikhil.locationalarm.model;

/**
 * "status": {
 * "code": 200,
 * "message": "Success"
 * }
 */
public class ResponseModel extends NetworkModel {
    private NetworkStatus status;

    public NetworkStatus getStatus() {
        return status;
    }

    public void setStatus(NetworkStatus status) {
        this.status = status;
    }
}
