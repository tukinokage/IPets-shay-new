package com.shay.loginandregistermodule.data.entity.result;

public class ComfrimPhoneResult {
    String errorMsg = "";
    String phoneToken;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPhoneToken() {
        return phoneToken;
    }

    public void setPhoneToken(String phoneToken) {
        this.phoneToken = phoneToken;
    }
}
