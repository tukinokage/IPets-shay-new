package com.shay.baselibrary.dto;

import java.io.Serializable;

public class Pet implements Serializable {
    int petClass;
    private String petId;
    private String petHeadImg;
    private String petName;
    private String petEnglishName;
    private String femaleWeight;
    private String maleWeight;
    private int viewNum;
    private String originPlace;
    private int fetchLevel;
    private int shapeLevel;
    
    public int getFetchLevel() {
        return fetchLevel;
    }

    public void setFetchLevel(int fetchLevel) {
        this.fetchLevel = fetchLevel;
    }

    public int getShapeLevel() {
        return shapeLevel;
    }

    public void setShapeLevel(int shapeLevel) {
        this.shapeLevel = shapeLevel;
    }



    public int getPetClass() {
        return petClass;
    }

    public void setPetClass(int petClass) {
        this.petClass = petClass;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetHeadImg() {
        return petHeadImg;
    }

    public void setPetHeadImg(String petHeadImg) {
        this.petHeadImg = petHeadImg;
    }
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

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }
}
