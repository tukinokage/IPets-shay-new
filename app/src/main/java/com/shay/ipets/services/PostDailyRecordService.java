package com.shay.ipets.services;


import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.ipets.entity.responses.PostDaliyResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostDailyRecordService {

    @POST(UrlUtil.DAILY_RECORD_URL.POST_DAILY_RECORD_URL)
    @FormUrlEncoded
    Observable<BaseResponse<PostDaliyResponse>> postDaliy(@FieldMap HashMap<String, Object> paramsMap);
}
