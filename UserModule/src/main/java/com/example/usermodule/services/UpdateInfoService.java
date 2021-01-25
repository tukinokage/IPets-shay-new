package com.example.usermodule.services;

import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UpdateInfoService {
    @POST("/")
    @FormUrlEncoded
    Observable<BaseResponse<UpdateUserInfoResponse>> updateUserInfo(@FieldMap HashMap<String, Object> map);

    @POST("/")
    @Multipart
    Observable<BaseResponse<UpdateUserInfoResponse>> updateBg(@PartMap HashMap<String, ResponseBody> responseBodyHashMap);

    @POST("/")
    @Multipart
    Observable<BaseResponse<UpdateUserInfoResponse>> updateHeadImg(@PartMap HashMap<String, ResponseBody> responseBodyHashMap);
}
