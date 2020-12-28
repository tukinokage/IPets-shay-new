package com.shay.loginandregistermodule.data.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.loginandregistermodule.data.entity.responsedata.SetPwResponseData;
import com.shay.loginandregistermodule.data.services.UserUrlService;
import java.util.HashMap;
import io.reactivex.Observable;

public class SetPwDataSource {
    public Observable<BaseResponse<SetPwResponseData>> setPw(HashMap<String, Object> paramsMap){
        UserUrlService service = new HttpUtil().getService(UserUrlService.class, UrlUtil.BASE_URL.BASE_URL);
        return service.setPassword(paramsMap);
    }
}
