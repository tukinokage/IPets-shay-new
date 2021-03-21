package com.example.usermodule.datasource;

import com.example.usermodule.entity.responses.GetDailyRecordResponse;
import com.example.usermodule.entity.responses.GetStarPetListResponse;
import com.example.usermodule.entity.responses.GetUserCommentResponse;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.StarPetResponse;
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

    public Observable<BaseResponse<GetStarPetListResponse>> getStarPetList(HashMap<String, Object> map){
        UserInfoService service = new HttpUtil().getService(UserInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.getStarPetListService(map);
    }

    public Observable<BaseResponse<GetUserCommentResponse>> getCommentList(HashMap<String, Object> map){
        UserInfoService service = new HttpUtil().getService(UserInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.getUserCommentService(map);
    }
    public Observable<BaseResponse<GetDailyRecordResponse>> getDailyRecordList(HashMap<String, Object> map){
        UserInfoService service = new HttpUtil().getService(UserInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.getDailyRecordService(map);
    }


    //star
    public Observable<BaseResponse<StarPetResponse>> starPet(HashMap<String, Object> paramsMasp){
        UserInfoService service = new HttpUtil().getService(UserInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.unStarPet(paramsMasp);
    }
}
