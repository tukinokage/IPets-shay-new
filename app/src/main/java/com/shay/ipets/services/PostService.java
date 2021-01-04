package com.shay.ipets.services;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.ipets.entity.responses.PostResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface PostService {
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<PostResponse>> postNew(@FieldMap HashMap<String, Object> map);

    /*@POST("/")
    @Multipart*/
}
