package com.shay.baselibrary.NetUtil;

import android.util.Base64;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.TestUser;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiUrl {
    @POST("ls/login")
    @FormUrlEncoded//表单提交
    Observable<BaseResponse<TestUser>> test(@FieldMap HashMap<String, Object> map);
    //Observable<Result<BaseResponse<T>>> test(@Body RequestBody requestBody);
}
