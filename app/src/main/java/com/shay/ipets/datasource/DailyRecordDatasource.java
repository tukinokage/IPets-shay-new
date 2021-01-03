package com.shay.ipets.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.ipets.entity.responses.PostDaliyResponse;
import com.shay.ipets.services.PostDailyRecordService;

import java.util.HashMap;

import io.reactivex.Observable;

public class DailyRecordDatasource {

    public Observable<BaseResponse<PostDaliyResponse>> postDaily(HashMap<String, Object> paramsMap){
        PostDailyRecordService service = new HttpUtil().getService(PostDailyRecordService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.postDaliy(paramsMap);
    }
}
