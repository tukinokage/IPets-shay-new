package com.shay.loginandregistermodule.data.services;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.AliSmsResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.CheckPhoneRepData;
import com.shay.loginandregistermodule.data.entity.responsedata.PhoneReponseData;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PhoneSmsService {
    @POST("/")
    @FormUrlEncoded
    Observable<AliSmsResponse> sendMsg(@FieldMap Map<String, Object> map);

    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<PhoneReponseData>> sendPhoneNum(@FieldMap HashMap<String, Object> map);


    //检查手机是否是新用户，并返回注册/等登录后的token
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<CheckPhoneRepData>> checkPhoneUser(@FieldMap HashMap<String, Object> map);
}
