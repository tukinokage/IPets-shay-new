package com.shay.loginandregistermodule.data.services;

import android.webkit.URLUtil;

import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.TestUser;
import com.shay.loginandregistermodule.data.entity.responsedata.LoginResponseData;
import com.shay.loginandregistermodule.data.entity.responsedata.SetPwResponseData;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

import retrofit2.http.PartMap;

public interface UserUrlService  {
    @POST(UrlUtil.USER_URL.LOGIN_URL)
    @FormUrlEncoded//表单提交
    //Call<BaseResponse<TestUser>> test(@FieldMap HashMap<String, Object> map);
    Observable<BaseResponse<LoginResponseData>> login(@FieldMap HashMap<String, Object> map);
    //Observable<Result<BaseResponse<T>>> test(@Body RequestBody requestBody);

    @POST(UrlUtil.USER_URL.UPDATE_PW_URL)
    @FormUrlEncoded
   Observable<BaseResponse<SetPwResponseData>> setPassword(@FieldMap HashMap<String, Object> map);


    @Multipart
    @POST("pic/upload")
    Observable<BaseResponse<TestUser>> uploadTest(@FieldMap HashMap<String, Object> fieldMap, @PartMap HashMap<String, Object> partMap);


}
