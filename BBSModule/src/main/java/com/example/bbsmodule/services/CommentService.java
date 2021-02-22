package com.example.bbsmodule.services;

import com.example.bbsmodule.entity.response.CommitCommentResponse;
import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;

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

public interface CommentService {
    @POST(UrlUtil.COMMENT_URL.POST_COMMENT_URL)
    @FormUrlEncoded
    Observable<BaseResponse<CommitCommentResponse>> commitComment(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.COMMENT_URL.UPLOAD_COMMENT_PIC_URL)
    @Multipart
    Observable<BaseResponse<UpLoadPicResponse>> uploadPic(@PartMap HashMap<String, RequestBody> responseBodyHashMap, @Part MultipartBody.Part body);
}
