package com.shay.loginandregistermodule.data.entity.responsedata;

public class PhoneReponseData {


    //0为新用户，1为已注册用户
    int userType;
    String token;

    public PhoneReponseData() {
    }
    public PhoneReponseData(int userType, String token) {
        this.userType = userType;
        this.token = token;
    }
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
