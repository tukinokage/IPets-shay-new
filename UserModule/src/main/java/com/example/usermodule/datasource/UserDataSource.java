package com.example.usermodule.datasource;

import com.example.usermodule.entity.UserInfo;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.services.UserInfoService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class UserDataSource {
    public Observable<BaseResponse<GetUserInfoResponse>> getUserInfo(HashMap<String, Object> map){
        UserInfoService service = new HttpUtil().getService(UserInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.getUserInfo(map);
    }
}
