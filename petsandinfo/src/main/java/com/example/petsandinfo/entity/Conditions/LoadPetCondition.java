package com.example.petsandinfo.entity.Conditions;

import java.io.Serializable;

public class LoadPetCondition implements Serializable {
    int shapeLevel;
    int fetchLevel;
    int rankType;
    int petClass;
    int perPageCount;
    int currentPageNum;

    public int getPerPageCount() {
        return perPageCount;
    }

    public void setPerPageCount(int perPageCount) {
        this.perPageCount = perPageCount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

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
