package com.example.usermodule.datasource;

import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class UpdateInfoDataSource {

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserInfo(HashMap<String, Object> map){

    }

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserHeadIcon(HashMap<String, ResponseBody> map){

    }

    public Observable<BaseResponse<UpdateUserInfoResponse>> updateUserBg(HashMap<String, ResponseBody> map){

    }


}
