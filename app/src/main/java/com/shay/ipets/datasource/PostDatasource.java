package com.shay.ipets.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.ipets.entity.responses.PostResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;
import com.shay.ipets.services.PostService;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class PostDatasource {
    public Observable<BaseResponse<PostResponse>> postNew(HashMap<String, Object> map){
        PostService service = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<PostResponse>> observable = service.postNew(map);
        return observable;
    }


    public Observable<BaseResponse<UpLoadPicResponse>> uploadPic(HashMap<String, ResponseBody> responseBodyHashMap){
        PostService service = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<UpLoadPicResponse>> observable = service.uploadPic(responseBodyHashMap);
        return observable;
    }

}
