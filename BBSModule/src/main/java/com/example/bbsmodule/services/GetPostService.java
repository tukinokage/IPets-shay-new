package com.example.bbsmodule.services;

import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetPostService {
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetPostListResult>> getList(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetPostInfoResponse>> getPostInfo(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetCommentResponse>> getPostComment(@FieldMap HashMap<String, Object> map);

}
