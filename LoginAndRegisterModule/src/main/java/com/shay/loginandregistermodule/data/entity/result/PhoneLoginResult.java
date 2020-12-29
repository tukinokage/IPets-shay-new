package com.shay.loginandregistermodule.data.entity.result;

public class PhoneLoginResult {
    private String errorMsg = "";
    //
    private int Type;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
