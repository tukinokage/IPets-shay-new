package com.example.usermodule.repository;

import android.text.GetChars;

import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.entity.params.GetUserCommentParam;
import com.example.usermodule.entity.params.GetUserDailyRecordParam;
import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.params.GetUserPetParam;
import com.example.usermodule.entity.params.StarPetParam;
import com.example.usermodule.entity.responses.GetDailyRecordResponse;
import com.example.usermodule.entity.responses.GetStarPetListResponse;
import com.example.usermodule.entity.responses.GetUserCommentResponse;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.StarPetResponse;
import com.example.usermodule.entity.result.GetStarPetListResult;
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
    private GetResultListener getDailyRecordListener;
    private GetResultListener  getStarPetList;
    private StarResultListener starPetResultListener;

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

    public void getStarPetList(GetUserPetParam getUserPetParam, GetResultListener getStarPetList) throws Exception{
        this.getStarPetList = getStarPetList;
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(getUserPetParam);
        userDataSource.getStarPetList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GetStarPetListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<GetStarPetListResponse> response) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(response);
                        setStarPetList(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setStarPetList(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getCommentList(GetUserCommentParam getUserCommentParam, GetResultListener getCommentListener) throws Exception{
            this.getCommentListener = getCommentListener;
            HashMap map = (HashMap) ObjectTransformUtil.objectToMap(getUserCommentParam);
            userDataSource.getCommentList(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<GetUserCommentResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<GetUserCommentResponse> response) {
                            Result result = RetrofitOnResponseUtil.parseBaseResponse(response);
                             setCommentResult(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Result result = RetrofitOnErrorUtil.OnError(e);
                            setCommentResult(result);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }

        public void getDailyRecordList(GetUserDailyRecordParam getUserDailyRecordParam, GetResultListener getDailyRecordListener) throws Exception{
            this.getDailyRecordListener = getDailyRecordListener;
            HashMap map = (HashMap) ObjectTransformUtil.objectToMap(getUserDailyRecordParam);
            userDataSource.getDailyRecordList(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<GetDailyRecordResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<GetDailyRecordResponse> response) {
                            Result result = RetrofitOnResponseUtil.parseBaseResponse(response);
                             setGetDaliyRecord(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Result result = RetrofitOnErrorUtil.OnError(e);
                            setGetDaliyRecord(result);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }

    public void starPet(StarPetParam starPetParam, StarResultListener starPetResultListener){

        this.starPetResultListener = starPetResultListener;
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(starPetParam);
        userDataSource.starPet(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<StarPetResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<StarPetResponse> starPetResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(starPetResponseBaseResponse);
                        setStarPetResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setStarPetResult(result);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    public void setStarPetList(Result result){
        getStarPetList.getResult(result);
    }
    public void setCommentResult(Result result){
        getCommentListener.getResult(result);
    }
    public void setGetDaliyRecord(Result result){
        getDailyRecordListener.getResult(result);
    }
    public void setStarPetResult(Result result){
        starPetResultListener.getResult(result);
    }

    public void setStarPetList(StarPetParam starPetParam, StarResultListener starResultListener) {


    }


    public interface GetResultListener{
        void getResult(Result result);
    }


    public interface StarResultListener{
        void getResult(Result result);
    }
}
