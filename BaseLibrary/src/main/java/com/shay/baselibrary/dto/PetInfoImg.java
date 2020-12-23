package com.shay.baselibrary.dto;

import java.io.Serializable;
import java.util.List;

public class PetInfoImg implements Serializable {

    private List<String> imgList;
    private String petId;

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

}
