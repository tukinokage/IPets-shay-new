package com.example.bbsmodule.entity.result;

import com.shay.baselibrary.dto.Post;

public class GetPostInfoResult {
    Post post;
    String errorMsg = "";

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
