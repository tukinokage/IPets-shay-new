package com.example.petsandinfo.model.entity;

import java.io.Serializable;

public class Pet implements Serializable {
    private String petName;
    private String petEnglishName;
    private String femaleWeight;
    private String maleWeight;
    private String viewNum;
    private String originPlace;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetEnglishName() {
        return petEnglishName;
    }

    public void setPetEnglishName(String petEnglishName) {
        this.petEnglishName = petEnglishName;
    }

    public String getFemaleWeight() {
        return femaleWeight;
    }

    public void setFemaleWeight(String femaleWeight) {
        this.femaleWeight = femaleWeight;
    }

    public String getMaleWeight() {
        return maleWeight;
    }

    public void setMaleWeight(String maleWeight) {
        this.maleWeight = maleWeight;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }
}
