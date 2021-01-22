package com.example.usermodule.entity.responses;

import com.example.usermodule.entity.UserInfo;

public class GetUserInfoResponse {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
