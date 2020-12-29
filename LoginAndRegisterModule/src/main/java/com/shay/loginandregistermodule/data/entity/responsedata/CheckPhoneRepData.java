package com.shay.loginandregistermodule.data.entity.responsedata;

public class CheckPhoneRepData {

    //0新用户 1已有用户
    int userType;
    String token;
    String userId;
    String userName;

    public CheckPhoneRepData(int userType, String token) {
        this.userType = userType;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public CheckPhoneRepData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
