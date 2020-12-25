package com.example.petsandinfo.entity.result;

import android.text.TextUtils;

import com.example.petsandinfo.model.Hospital;

import java.util.List;

public class LoadHospitalResult {
    private List<Hospital> data;
    private String errorMsg = "";

    public boolean hasError(){
        if (TextUtils.isEmpty(errorMsg)){
            return false;
        }else {
            return true;
        }
    }

    public List<Hospital> getData() {
        return data;
    }

    public void setData(List<Hospital> data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
