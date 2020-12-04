package com.shay.loginandregistermodule.data;

import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.TestUser;
import com.shay.loginandregistermodule.data.model.LoggedInUser;
import com.shay.loginandregistermodule.data.services.UserUrlService;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public  Observable<BaseResponse<TestUser>> login(HashMap<String, Object> paramsMap){

            // TODO: handle loggedInUser authentication

            UserUrlService service = new HttpUtil().getService(UserUrlService.class, UrlUtil.BASE_URL.BASE_URL);
        Observable<BaseResponse<TestUser>> test = service.test(paramsMap);
        return test;
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public interface LoginDataSourceListener{
        void getResult(Result result);
    }

    class MyException extends Exception{

        public MyException(String message) {
            super(message);
        }
    }
}
