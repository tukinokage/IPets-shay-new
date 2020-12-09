package com.shay.loginandregistermodule.data.model;

import java.io.Serializable;

public class AliSmsResponse implements Serializable {
    String BizId;
    //OK即为成功
    String Code;
    String Message;
    String RequestId;

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        BizId = bizId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }


}
