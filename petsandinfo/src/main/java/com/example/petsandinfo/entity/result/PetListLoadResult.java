package com.example.petsandinfo.entity.result;

import android.text.TextUtils;

import com.shay.baselibrary.dto.Pet;

import java.util.List;

public class PetListLoadResult {
    private List<Pet> data;
    private String errorMsg = "";
    public boolean hasError(){
        if (TextUtils.isEmpty(errorMsg)){
            return false;
        }else {
            return true;
        }
    }

    public List<Pet> getData() {
        return data;
    }

    public void setData(List<Pet> data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
