package com.example.petsandinfo.entity.Conditions;

public class LoadPetStoreCondition {


    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    private String petId;

    public LoadPetStoreCondition(String petId) {
        this.petId = petId;
    }
}
