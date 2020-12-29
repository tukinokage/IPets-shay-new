package com.shay.loginandregistermodule.data.repository;

import android.os.Looper;
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

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
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
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
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

    public void login(HashMap<String, Object> paramMap, final ResultListener resultListener) {
        // handle login

        this.resultListener = resultListener;
        try{
            dataSource.login(paramMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<TestUser>>()
                    {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<TestUser> testUserBaseResponse) {

                            if (testUserBaseResponse == null){
                                Log.d( "登录接收", "空");
                                return;
                            }

                            Result result = null;
                            if("".equals(testUserBaseResponse.getErrorMsg())){
                                TestUser testUser = testUserBaseResponse.getData();
                                result = new Result.Success(testUser);
                                Log.d(this.getClass().getSimpleName(), "onNext线程：" + (Looper.myLooper() == Looper.myLooper()));

                            }else {
                                Log.d( "登录失败", testUserBaseResponse.getErrorMsg());
                                result = new Result.Error(new MyException(testUserBaseResponse.getErrorMsg()));
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
                .map(new Function<SPUserInfo, Boolean>() {
                    @Override
                    public Boolean apply(SPUserInfo userInfo) throws Exception {
                        HashMap<String, String>  hashMap = new HashMap<>();
                        UserInfoUtil.saveUserToken(userInfo.getToken());
                        UserInfoUtil.saveUserId(userInfo.getUserId());
                        UserInfoUtil.saveUserName(userInfo.getUserName());
                        return true;
                    }
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
