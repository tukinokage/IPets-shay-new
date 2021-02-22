package com.example.bbsmodule.entity.params;

public class GetPostListParam {
    String searchCondition;
    String type;
    String perPaperNum;
    String currentPaperNum;
    String searchUid;

    public String getSearchUid() {
        return searchUid;
    }

    public void setSearchUid(String searchUid) {
        this.searchUid = searchUid;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerPaperNum() {
        return perPaperNum;
    }

    public void setPerPaperNum(String perPaperNum) {
        this.perPaperNum = perPaperNum;
    }

    public String getCurrentPaperNum() {
        return currentPaperNum;
    }

    public void setCurrentPaperNum(String currentPaperNum) {
        this.currentPaperNum = currentPaperNum;
    }
}
