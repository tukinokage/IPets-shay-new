package com.example.bbsmodule.datasource;

import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.services.GetPostService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import java.util.HashMap;

import io.reactivex.Observable;


public class PostInfoDatasource {
    public Observable<BaseResponse<GetPostInfoResponse>> getPostInfo(HashMap<String, Object> map){

        GetPostService postService = new HttpUtil().getService(GetPostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getPostInfo(map);
    }

    public Observable<BaseResponse<GetCommentResponse>> getPostCommentList(HashMap<String, Object> map){
        GetPostService postService = new HttpUtil().getService(GetPostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getPostComment(map);
    }

}