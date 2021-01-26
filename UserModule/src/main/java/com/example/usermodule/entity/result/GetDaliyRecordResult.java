package com.example.usermodule.entity.result;

import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.UserDailyRecordItem;

import java.util.List;

public class GetDaliyRecordResult {
    String errorMsg = "";
    List<UserDailyRecordItem> dailyRecordItemList;

    public List<UserDailyRecordItem> getDailyRecordItemList() {
        return dailyRecordItemList;
    }

    public void setDailyRecordItemList(List<UserDailyRecordItem> dailyRecordItemList) {
        this.dailyRecordItemList = dailyRecordItemList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
