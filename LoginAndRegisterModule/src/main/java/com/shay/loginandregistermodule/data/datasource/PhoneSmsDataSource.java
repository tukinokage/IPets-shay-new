package com.shay.loginandregistermodule.data.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.AliSmsResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.PhoneReponseData;
import com.shay.loginandregistermodule.data.entity.responsedata.SmsResponse;
import com.shay.loginandregistermodule.data.services.PhoneSmsService;

import java.util.HashMap;

import io.reactivex.Observable;

public class PhoneSmsDataSource {

    public PhoneSmsDataSource() {
    }

    public Observable<BaseResponse<SmsResponse>> sendMsg(HashMap<String, Object> map){
        PhoneSmsService smsService = new HttpUtil().getService(PhoneSmsService.class, UrlUtil.BASE_URL.BASE_URL);
        return smsService.sendMsg(map);
    }

    public Observable<BaseResponse<PhoneReponseData>> sendPhoneNum(HashMap<String, Object> map){
        PhoneSmsService smsService = new HttpUtil().getService(PhoneSmsService.class, UrlUtil.BASE_URL.BASE_URL);
        return smsService.sendPhoneNum(map);
    }


}
