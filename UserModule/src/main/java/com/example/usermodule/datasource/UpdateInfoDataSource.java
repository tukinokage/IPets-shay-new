package com.example.usermodule.datasource;

import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.example.usermodule.services.UpdateInfoService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class UpdateInfoDataSource {

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserInfo(HashMap<String, Object> map){
        UpdateInfoService service = new HttpUtil().getService(UpdateInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.updateUserInfo(map);
    }

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserHeadIcon(HashMap<String, ResponseBody> map){
        UpdateInfoService service = new HttpUtil().getService(UpdateInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.updateHeadImg(map);
    }

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserBg(HashMap<String, ResponseBody> map){
        UpdateInfoService service = new HttpUtil().getService(UpdateInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.updateBg(map);
    }


}
