package com.example.bbsmodule.entity.response;

import com.shay.baselibrary.dto.Comment;

import java.util.List;

public class GetCommentResponse {
    List<Comment> commentsList;

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
