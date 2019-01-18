package com.nikhil.locationalarm.model;

/**
 * "userId": 1370,
 * "name": "INTERVIEWERON",
 * "role": "INTERVIEWER",
 * "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNDAzMjQzNDg5Iiwicm9sZSI6IklOVEVSVklFV0VSIiwibmFtZSI6IklOVEVSVklFV0VST04ifQ.VQ0Eo4hrpZFyaTkPdf6UtJaHsDkoUR1iOl5KGoBI1zIWfb0DTXkoU0koi_LDKlhcti93J2hW0t2rZfFf2YxoiA",
 * "firstTimeLogin": true
 */
public class UserInfo {
    private long userId;
    private String name;
    private String role;
    private String token;
    private boolean firstTimeLogin;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }
}
