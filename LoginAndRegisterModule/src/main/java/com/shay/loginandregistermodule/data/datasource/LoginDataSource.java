package com.shay.loginandregistermodule.data.datasource;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.dto.User;
import com.shay.loginandregistermodule.data.entity.responsedata.CheckPhoneRepData;
import com.shay.loginandregistermodule.data.entity.responsedata.LoginResponseData;
import com.shay.loginandregistermodule.data.services.PhoneSmsService;
import com.shay.loginandregistermodule.data.services.UserUrlService;

import java.util.HashMap;

import io.reactivex.Observable;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public  Observable<BaseResponse<LoginResponseData>> login(HashMap<String, Object> paramsMap){

            // TODO: handle loggedInUser authentication

            UserUrlService service = new HttpUtil().getService(UserUrlService.class, UrlUtil.BASE_URL.BASE_URL);
            Observable<BaseResponse<LoginResponseData>> observable = service.login(paramsMap);
            return observable;
    }


    public  Observable<BaseResponse<CheckPhoneRepData>> checkPhoneUser(HashMap<String, Object> paramsMap){
        PhoneSmsService service = new HttpUtil().getService(PhoneSmsService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<CheckPhoneRepData>> observable = service.checkPhoneUser(paramsMap);
        return observable;
    }

    public void logout() {
        // TODO: revoke authentication
    }


}
