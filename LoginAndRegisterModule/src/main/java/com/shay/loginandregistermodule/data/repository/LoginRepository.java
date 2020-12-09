package com.shay.loginandregistermodule.data.repository;

import android.os.Looper;
import android.util.Log;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.myexceptions.MyException;
import com.shay.loginandregistermodule.data.datasource.LoginDataSource;

import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    //net dataSource operate completed next
    private void setLoginResult(Result result){

        resultListener.returnResult(result);
    }


    public interface ResultListener{
        void returnResult(Result result);
    }


}
