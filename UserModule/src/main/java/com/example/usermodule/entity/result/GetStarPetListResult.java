package com.example.usermodule.entity.result;

import com.shay.baselibrary.dto.Pet;

import java.util.List;

public class GetStarPetListResult {
    String errorMsg = "";
    List<Pet> petList;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
