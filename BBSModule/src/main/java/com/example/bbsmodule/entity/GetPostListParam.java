package com.example.bbsmodule.entity;

public class GetPostListParam {
        String searchCondition;
        int type;
        int perPaperNum;
        int currentPaperNum;

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPerPaperNum() {
        return perPaperNum;
    }

    public void setPerPaperNum(int perPaperNum) {
        this.perPaperNum = perPaperNum;
    }

    public int getCurrentPaperNum() {
        return currentPaperNum;
    }

    public void setCurrentPaperNum(int currentPaperNum) {
        this.currentPaperNum = currentPaperNum;
    }
}
