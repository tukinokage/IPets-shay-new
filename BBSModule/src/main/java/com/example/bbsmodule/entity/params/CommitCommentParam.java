package com.example.bbsmodule.entity.params;

import com.shay.baselibrary.dto.Picture;

import java.util.List;

public class CommitCommentParam {
    String token;
    String userId;
    String postId;
    String commentText;
    List<String> picList;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentText() {
        return commentText;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
