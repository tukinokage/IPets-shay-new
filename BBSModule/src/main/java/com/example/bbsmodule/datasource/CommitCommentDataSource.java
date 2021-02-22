package com.example.bbsmodule.datasource;

import com.example.bbsmodule.entity.response.CommitCommentResponse;
import com.example.bbsmodule.services.CommentService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CommitCommentDataSource {
    public Observable<BaseResponse<CommitCommentResponse>> commentNew(HashMap<String, Object> map){
        CommentService service = new HttpUtil().getService(CommentService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<CommitCommentResponse>> observable = service.commitComment(map);
        return observable;
    }


    public Observable<BaseResponse<UpLoadPicResponse>> uploadPic(HashMap<String, RequestBody> responseBodyHashMap, MultipartBody.Part part){
        CommentService service = new HttpUtil().getService(CommentService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<UpLoadPicResponse>> observable = service.uploadPic(responseBodyHashMap, part);
        return observable;
    }
}
