package com.shay.loginandregistermodule.data.entity.params;

public class SetPwRequestParam {
    String userId;
    String password;

    public SetPwRequestParam(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}