package com.shay.loginandregistermodule.data.entity.responsedata;

public class PhoneReponseData {

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
