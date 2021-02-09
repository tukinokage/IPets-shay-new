package com.example.bbsmodule.services;

import android.webkit.URLUtil;

import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.UrlInfoUtil.*;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetPostService {
    @POST(UrlUtil.POST_URL.GET_POST_LIST_URL)
    @FormUrlEncoded
    Observable<BaseResponse<GetPostListResult>> getList(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.POST_URL.GET_POST_INFO_URL)
    @FormUrlEncoded
    Observable<BaseResponse<GetPostInfoResponse>> getPostInfo(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.COMMENT_URL.GET_COMMENT_LIST_URL)
    @FormUrlEncoded
    Observable<BaseResponse<GetCommentResponse>> getPostComment(@FieldMap HashMap<String, Object> map);

}
