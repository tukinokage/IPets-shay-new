package com.example.petsandinfo.entity.result;

public class CheckPetIsStarResult {
    boolean isStar;
    String errorMsg = "";

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
