package com.example.usermodule.entity.responses;

import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.UserDailyRecordItem;

import java.util.List;

public class GetDailyRecordResponse {
    List<UserDailyRecordItem> dailyRecordItemList;
    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public List<UserDailyRecordItem> getDailyRecordItemList() {
        return dailyRecordItemList;
    }

    public void setDailyRecordItemList(List<UserDailyRecordItem> dailyRecordItemList) {
        this.dailyRecordItemList = dailyRecordItemList;
    }
}
