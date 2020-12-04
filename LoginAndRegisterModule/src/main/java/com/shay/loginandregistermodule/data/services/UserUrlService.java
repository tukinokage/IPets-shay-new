package com.shay.loginandregistermodule.data.services;

import android.util.Base64;

import com.shay.baselibrary.NetUtil.IHttpService;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.TestUser;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserUrlService extends IHttpService {
    @POST("ls/login")
    @FormUrlEncoded//表单提交
    Observable<BaseResponse<TestUser>> test(@FieldMap HashMap<String, Object> map);
    //Observable<Result<BaseResponse<T>>> test(@Body RequestBody requestBody);

    @Multipart
    @POST("pic/upload")
    Observable<BaseResponse<TestUser>> uploadTest(@FieldMap HashMap<String, Object> fieldMap, @PartMap HashMap<String, Object> partMap);
}
