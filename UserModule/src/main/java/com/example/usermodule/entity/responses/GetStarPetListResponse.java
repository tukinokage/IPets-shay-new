package com.example.usermodule.entity.responses;

import com.shay.baselibrary.dto.Pet;

import java.util.List;

public class GetStarPetListResponse {
    List<Pet> petList;

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
