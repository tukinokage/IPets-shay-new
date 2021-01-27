package com.example.usermodule.entity.responses;

import com.shay.baselibrary.dto.UserCommentItem;

import java.util.List;

public class GetUserCommentResponse {

    private List<UserCommentItem> commentItemList;
    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public List<UserCommentItem> getCommentItemList() {
        return commentItemList;
    }

    public void setCommentItemList(List<UserCommentItem> commentItemList) {
        this.commentItemList = commentItemList;
    }
}
