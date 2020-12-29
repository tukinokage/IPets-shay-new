package com.shay.loginandregistermodule.data.entity.params;

public class ConfrimPhoneNumParam {
    String phoneNum;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;

    public ConfrimPhoneNumParam() {
    }

    public ConfrimPhoneNumParam(String phoneNum, String code) {
        this.phoneNum = phoneNum;
        this.code = code;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
