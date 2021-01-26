package com.example.usermodule.services;

import com.example.usermodule.entity.params.GetUserDailyRecordParam;
import com.example.usermodule.entity.responses.GetDailyRecordResponse;
import com.example.usermodule.entity.responses.GetStarPetListResponse;
import com.example.usermodule.entity.responses.GetUserCommentResponse;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInfoService {
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetUserInfoResponse>> getUserInfo(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetStarPetListResponse>> getStarPetListService(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetUserCommentResponse>> getUserCommentService(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetDailyRecordResponse>> getDailyRecordService(@FieldMap HashMap<String, Object> map);


}
