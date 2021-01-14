package com.example.bbsmodule.entity.response;

import com.example.bbsmodule.entity.BBSPost;

import java.util.List;

public class GetPostListResponse {
    List<BBSPost> bbsPostList;
    boolean hasMore;

    public List<BBSPost> getBbsPostList() {
        return bbsPostList;
    }

    public void setBbsPostList(List<BBSPost> bbsPostList) {
        this.bbsPostList = bbsPostList;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

}