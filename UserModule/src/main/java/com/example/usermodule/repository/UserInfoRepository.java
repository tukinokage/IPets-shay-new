package com.example.usermodule.repository;

import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserInfoRepository {
    private UserDataSource userDataSource;
    private GetResultListener getUserInfoListener;
    private GetResultListener getCommentListener;
    private volatile static UserInfoRepository instance;

    public UserInfoRepository(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    synchronized public static UserInfoRepository getInstance(UserDataSource userDataSource){
        if(instance == null){
            instance = new UserInfoRepository(userDataSource);
        }
        return instance;
    }

    public void getUserInfo(GetUserInfoParam getUserInfoParam, GetResultListener getUserInfoListener) throws Exception{
        this.getUserInfoListener = getUserInfoListener;
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(getUserInfoParam);
        userDataSource.getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GetUserInfoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<GetUserInfoResponse> getUserInfoResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(getUserInfoResponseBaseResponse);
                        setGetUserInfoResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setGetUserInfoResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void setGetUserInfoResult(Result result){
        getUserInfoListener.getResult(result);
    }

    public interface GetResultListener{
        void getResult(Result result);
    }

}
