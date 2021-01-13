package com.example.bbsmodule.entity.result;

import com.example.bbsmodule.entity.BBSPost;

import java.util.List;

public class GetPostListResult {
    List<BBSPost> bbsPostList;
    String errorMg = "";

    public List<BBSPost> getBbsPostList() {
        return bbsPostList;
    }

    public void setBbsPostList(List<BBSPost> bbsPostList) {
        this.bbsPostList = bbsPostList;
    }

    public String getErrorMg() {
        return errorMg;
    }

    public void setErrorMg(String errorMg) {
        this.errorMg = errorMg;
    }
}
