package com.shay.loginandregistermodule.data.entity.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.shay.baselibrary.dto.Result;

import java.io.Serializable;

public class ConfrimPhoneResult implements Serializable {
    String errorMsg = "";
    String phoneToken;

    String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

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
