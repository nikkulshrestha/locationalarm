package com.nikhil.locationalarm.model;

import java.util.Map;

public class RawDataModel extends NetworkModel {

    private String rawContent;
    private Map<String, String> headers;

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
