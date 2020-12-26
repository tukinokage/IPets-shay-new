package com.example.petsandinfo.entity.Conditions;

public class LoadPetIntroductionCondition {
    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    private String petId;

    public LoadPetIntroductionCondition(String petId) {
        this.petId = petId;
    }
}
