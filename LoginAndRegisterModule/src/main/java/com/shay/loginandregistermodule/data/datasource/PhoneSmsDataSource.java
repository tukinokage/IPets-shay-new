package com.shay.loginandregistermodule.data.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.loginandregistermodule.data.services.PhoneSmsService;

import java.util.HashMap;

import io.reactivex.Observable;

public class PhoneSmsDataSource {

    public PhoneSmsDataSource() {
    }

    public Observable sendAliApiMsg(HashMap<String, Object> map){
        PhoneSmsService smsService = new HttpUtil().getService(PhoneSmsService.class, UrlUtil.ALI_API_URL.SEND_PHONE_MSG);
        return smsService.sendMsg(map);
    }


}
