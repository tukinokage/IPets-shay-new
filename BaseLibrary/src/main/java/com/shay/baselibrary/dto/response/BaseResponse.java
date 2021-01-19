package com.shay.baselibrary.dto.response;

import java.io.Serializable;

public class BaseResponse <T> implements Serializable {

    private String errorMsg = "";
    private T data;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseResponse() {
    }

    public BaseResponse(String errorMsg, T data) {
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
