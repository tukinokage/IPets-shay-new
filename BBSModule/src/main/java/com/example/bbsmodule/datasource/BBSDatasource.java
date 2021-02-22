package com.example.bbsmodule.datasource;

import com.example.bbsmodule.entity.response.GetPostListResponse;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.services.GetPostService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class BBSDatasource {
    public Observable<BaseResponse<GetPostListResponse>> getGetList(HashMap<String, Object> map){

        GetPostService postService = new HttpUtil().getService(GetPostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getList(map);
    }

}
