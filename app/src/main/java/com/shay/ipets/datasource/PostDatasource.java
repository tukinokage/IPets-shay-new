package com.shay.ipets.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.ipets.entity.responses.PostResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;
import com.shay.ipets.services.PostService;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;

public class PostDatasource {
    public Observable<BaseResponse<PostResponse>> postNew(HashMap<String, RequestBody> map){
        PostService service = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<PostResponse>> observable = service.postNew(map);
        return observable;
    }


    public Observable<BaseResponse<UpLoadPicResponse>> uploadPic(HashMap<String, RequestBody> responseBodyHashMap, MultipartBody.Part body){
        PostService service = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<UpLoadPicResponse>> observable = service.uploadPic(responseBodyHashMap, body);
        return observable;
    }

}
