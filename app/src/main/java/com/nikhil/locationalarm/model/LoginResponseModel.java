package com.nikhil.locationalarm.model;

/**
 * "status": {
 * "code": 200,
 * "message": "Success"
 * },
 * "result": {
 * "userId": 1370,
 * "name": "INTERVIEWERON",
 * "role": "INTERVIEWER",
 * "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNDAzMjQzNDg5Iiwicm9sZSI6IklOVEVSVklFV0VSIiwibmFtZSI6IklOVEVSVklFV0VST04ifQ.VQ0Eo4hrpZFyaTkPdf6UtJaHsDkoUR1iOl5KGoBI1zIWfb0DTXkoU0koi_LDKlhcti93J2hW0t2rZfFf2YxoiA",
 * "firstTimeLogin": true
 * }
 */
public class LoginResponseModel extends ResponseModel {
    private UserInfo result;

    public UserInfo getResult() {
        return result;
    }

    public void setResult(UserInfo result) {
        this.result = result;
    }
}
