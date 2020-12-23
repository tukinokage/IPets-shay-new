package com.example.petsandinfo.model.entity;

import java.io.Serializable;

public class LoadPetCondition implements Serializable {
    int shapeLevel;
    int fetchLevel;
    int rankType;
    int petClass;

    public int getShapeLevel() {
        return shapeLevel;
    }

    public void setShapeLevel(int shapeLevel) {
        this.shapeLevel = shapeLevel;
    }

    public int getFetchLevel() {
        return fetchLevel;
    }

    public void setFetchLevel(int fetchLevel) {
        this.fetchLevel = fetchLevel;
    }

    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    public int getPetClass() {
        return petClass;
    }

    public void setPetClass(int petClass) {
        this.petClass = petClass;
    }


}
