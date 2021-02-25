package com.shay.ipets.services;

import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;
import com.shay.baselibrary.UrlInfoUtil.*;
import com.shay.ipets.entity.responses.PostResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface PostService {
    @POST(UrlUtil.POST_URL.POST_NEW_URL)
    @Multipart
    Observable<BaseResponse<PostResponse>> postNew(@PartMap HashMap<String, RequestBody> map);

    @POST(UrlUtil.POST_URL.UPLOAD_PIC_URL)
    @Multipart
    Observable<BaseResponse<UpLoadPicResponse>> uploadPic(@PartMap HashMap<String, RequestBody> responseBodyHashMap, @Part MultipartBody.Part body);
}
