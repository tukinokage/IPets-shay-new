package com.example.usermodule.services;

import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UpdateInfoService {
    @POST(UrlUtil.USER_URL.UPDATE_INFO_URL)
    @FormUrlEncoded
    Observable<BaseResponse<UpdateUserInfoResponse>> updateUserInfo(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.USER_URL.UPDATE_BG_URL)
    @Multipart
    Observable<BaseResponse<UpdateUserInfoResponse>> updateBg(@PartMap HashMap<String, RequestBody> responseBodyHashMap);

    @POST(UrlUtil.USER_URL.UPDATE_HEAD_IMG_URL)
    @Multipart
    Observable<BaseResponse<UpdateUserInfoResponse>> updateHeadImg(@PartMap HashMap<String, RequestBody> responseBodyHashMap);
}
