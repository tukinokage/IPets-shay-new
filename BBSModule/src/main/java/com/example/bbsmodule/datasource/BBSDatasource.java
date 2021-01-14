package com.example.bbsmodule.datasource;

import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.services.PostService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class BBSDatasource {
    public Observable<BaseResponse<GetPostListResult>> getGetList(HashMap<String, Object> map){

        PostService postService = new HttpUtil().getService(PostService.class, UrlUtil.BASE_URL.BASE_URL);
        return postService.getList(map);
    }

}
