package com.shay.loginandregistermodule.data.repository;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.SPUserInfo;
import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.myexceptions.MyException;
import com.shay.loginandregistermodule.data.datasource.LoginDataSource;
import com.shay.loginandregistermodule.data.entity.responsedata.CheckPhoneRepData;
import com.shay.loginandregistermodule.data.entity.responsedata.LoginResponseData;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;
    //回调给viewmodel
    private ResultListener resultListener;
    private ResultListener phoneResultListener;
    private ResultListener saveUserInfoListener;

    private TestUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    synchronized public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(TestUser user) {
        this.user = user;
    }


    public void checkPhone(HashMap<String, Object> paramMap, final ResultListener phoneResultListener) throws Exception{
        this.phoneResultListener = phoneResultListener;
        dataSource.checkPhoneUser(paramMap) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<CheckPhoneRepData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CheckPhoneRepData> checkPhoneRepDataBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(checkPhoneRepDataBaseResponse);
                        setCheckPhoneResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setCheckPhoneResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void login(HashMap<String, Object> paramMap, final ResultListener resultListener) throws Exception {
        // handle login

        this.resultListener = resultListener;
        try{
            dataSource.login(paramMap)
                    .subscribeOn(Schedulers.io())
                    .map(loginResponseDataBaseResponse -> {
                        if(TextUtils.isEmpty(loginResponseDataBaseResponse.getErrorMsg())){
                            LoginResponseData loginResponseData = loginResponseDataBaseResponse.getData();
                            UserInfoUtil.saveUserToken(loginResponseData.getToken());
                            UserInfoUtil.saveUserId(loginResponseData.getUserId());
                            UserInfoUtil.saveUserName(loginResponseData.getUserName());
                        }
                        return loginResponseDataBaseResponse;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<LoginResponseData>>()
                    {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<LoginResponseData> responseData) {

                            if (responseData == null){
                                Log.d( "登录接收", "空");
                                return;
                            }

                            Result result = null;
                            if("".equals(responseData.getErrorMsg())){
                                LoginResponseData data = responseData.getData();
                                result = new Result.Success(data);
                                Log.d(this.getClass().getSimpleName(), "onNext线程：" + (Looper.myLooper() == Looper.myLooper()));

                            }else {
                                Log.d( "登录失败", responseData.getErrorMsg());
                                result = new Result.Error(new MyException(responseData.getErrorMsg()));
                            }

                            setLoginResult(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            //errorResult
                           Result result =  RetrofitOnErrorUtil.OnError(e);
                           setLoginResult(result);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            setLoginResult(new Result.Error(new Exception("操作错误:201")));
        }

    }


    public void saveUserInfo(SPUserInfo userInfo, final ResultListener saveUserInfoListener){
        this.saveUserInfoListener = saveUserInfoListener;
        Observable.just(userInfo)
                .map(userInfo1 -> {
                    UserInfoUtil.saveUserToken(userInfo1.getToken());
                    UserInfoUtil.saveUserId(userInfo1.getUserId());
                    UserInfoUtil.saveUserName(userInfo1.getUserName());
                    return true;
                })
                .subscribeOn(Schedulers.io())//给map操作设置异步线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Result result;
                        if (aBoolean){
                            result = new Result.Success(aBoolean);
                        }else {
                            result =new Result.Error(new MyException("本地用户信息存储失败"));
                        }

                        setSaveInfoResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Result result =new Result.Error(new MyException("本地用户信息存储失败"));
                        setSaveInfoResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //net dataSource operate completed next
    private void setLoginResult(Result result){
        resultListener.returnResult(result);
    }

    private void setSaveInfoResult(Result result){ saveUserInfoListener.returnResult(result); }

    public interface ResultListener{
        void returnResult(Result result);
    }

    private void setCheckPhoneResult(Result result){
            resultListener.returnResult(result);
        }


}
