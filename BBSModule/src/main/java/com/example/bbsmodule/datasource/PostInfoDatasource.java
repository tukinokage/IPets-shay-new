package com.example.bbsmodule.datasource;

import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.services.PostService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import java.util.HashMap;

import io.reactivex.Observable;


public class PostInfoDatasource {
    public Observable<BaseResponse<GetPostInfoResponse>> getPostInfo(HashMap<String, Object> map){

        PostService postService = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getPostInfo(map);
    }

    public Observable<BaseResponse<GetCommentResponse>> getPostCommentList(HashMap<String, Object> map){
        PostService postService = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getPostComment(map);
    }

}