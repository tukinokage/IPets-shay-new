package com.shay.loginandregistermodule.data.services;

import com.shay.loginandregistermodule.data.entity.responsedata.AliSmsResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PhoneSmsService {
    @POST("/")
    @FormUrlEncoded
    Observable<AliSmsResponse> sendMsg(@FieldMap Map<String, Object> map);
}
