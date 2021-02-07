package com.example.petsandinfo.model;

import java.io.Serializable;

public class PetIntroduce implements Serializable {
    private String petId;
    private String petStory;
    private String petAttention;
    private String headImgName;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetStory() {
        return petStory;
    }

    public void setPetStory(String petStory) {
        this.petStory = petStory;
    }

    public String getPetAttention() {
        return petAttention;
    }

    public void setPetAttention(String petAttention) {
        this.petAttention = petAttention;
    }
}
