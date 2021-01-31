package com.shay.loginandregistermodule.data.entity.responsedata;

public class PhoneReponseData {

    //验证码正确后获取到的
    String phoneToken;
    public PhoneReponseData() {
    }
    public PhoneReponseData( String phoneToken) {
        this.phoneToken = phoneToken;
    }
    public String getPhoneToken() {
        return phoneToken;
    }
    public void setPhoneToken(String phoneToken) {
        this.phoneToken = phoneToken;
    }

}
