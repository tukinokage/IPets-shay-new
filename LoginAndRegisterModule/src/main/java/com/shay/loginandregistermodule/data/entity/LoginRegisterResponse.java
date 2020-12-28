package com.shay.loginandregistermodule.data.entity;

//登录注册时返回的数据格式
public class LoginRegisterResponse {
    String token;
    String userName;
    String userId;

    public LoginRegisterResponse() {
    }

    public LoginRegisterResponse(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
