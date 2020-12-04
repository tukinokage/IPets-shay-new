package com.shay.loginandregistermodule.data;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.TestUser;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        try{
            dataSource.login(paramMap).subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BaseResponse<TestUser>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<TestUser> testUserBaseResponse) {

                            TestUser testUser = testUserBaseResponse.getData();

                        }

                        @Override
                        public void onError(Throwable e) {

                            setLoginResult(new Result.Error(new Exception("操作错误:202")));
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }catch (Exception e){
            setLoginResult(new Result.Error(new Exception("操作错误:201")));
        }

    }

    private void setLoginResult(Result result){
        resultListener.returnResult(result);
    }


    public interface ResultListener{
        void returnResult(Result result);
    }


}
