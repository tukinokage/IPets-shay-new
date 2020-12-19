package com.example.petsandinfo.model.entity;

import com.shay.baselibrary.dto.Result;

import java.util.List;

public class PetListLoadResult {
    private List<Pet> data;
    private String errorMsg = "";

    public Result<Pet> getData() {
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
