package com.example.petsandinfo.entity.Conditions;

public class LoadPetPicCondition {


    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    private String petId;

    public LoadPetPicCondition(String petId) {
        this.petId = petId;
    }
}
