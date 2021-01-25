package com.example.usermodule.services;

import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInfoService {
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<GetUserInfoResponse>> getUserInfo(@FieldMap HashMap<String, Object> map);
}
