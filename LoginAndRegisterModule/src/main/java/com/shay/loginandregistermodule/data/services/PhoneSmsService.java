package com.shay.loginandregistermodule.data.services;

import android.webkit.URLUtil;

import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.AliSmsResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.CheckPhoneRepData;
import com.shay.loginandregistermodule.data.entity.responsedata.PhoneReponseData;
import com.shay.loginandregistermodule.data.entity.responsedata.SmsResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PhoneSmsService {

    /**
     * 服务器发送验证码
     * */
    @POST(UrlUtil.USER_URL.SMS_LOGIN_URL)
    @FormUrlEncoded
    Observable<BaseResponse<SmsResponse>> sendMsg(@FieldMap Map<String, Object> map);

    /**
     *
     *  获取phonetoken*/
    @POST(UrlUtil.USER_URL.COMMIT_PHONE_URL)
    @FormUrlEncoded
    Observable<BaseResponse<PhoneReponseData>> sendPhoneNum(@FieldMap HashMap<String, Object> map);


    //检查手机是否是新用户，并返回注册/等登录后的token
    @POST(UrlUtil.USER_URL.CHECK_PHONE_URL)
    @FormUrlEncoded
    Observable<BaseResponse<CheckPhoneRepData>> checkPhoneUser(@FieldMap HashMap<String, Object> map);
}
