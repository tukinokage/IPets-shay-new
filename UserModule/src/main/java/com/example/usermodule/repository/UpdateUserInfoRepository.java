package com.example.usermodule.repository;

import com.example.usermodule.datasource.UpdateInfoDataSource;
import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.params.UpdateUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateUserInfoRepository {
    private UpdateInfoDataSource updateInfoDataSource;

    private GetResultListener updateUserInfoListener;
    private GetResultListener updateHeadImgListener;
    private GetResultListener updateBgListener;
    private volatile static UpdateUserInfoRepository instance;

    public UpdateUserInfoRepository(UpdateInfoDataSource updateInfoDataSource) {
        this.updateInfoDataSource = updateInfoDataSource;
    }

    synchronized public static UpdateUserInfoRepository getInstance(UpdateInfoDataSource updateInfoDataSource ){
        if(instance == null){
            instance = new UpdateUserInfoRepository(updateInfoDataSource);
        }
        return instance;
    }

    public void  updateUserInfo(UpdateUserInfoParam updateUserInfoParam, GetResultListener updateUserInfoListener) throws Exception{
        this.updateUserInfoListener = updateUserInfoListener;
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(updateUserInfoParam);
        updateInfoDataSource.updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UpdateUserInfoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UpdateUserInfoResponse> updateUserInfoResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(updateUserInfoResponse);
                        setUpdatetUserInfoResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setUpdatetUserInfoResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }




    public void setUpdatetUserInfoResult(Result result){
        updateUserInfoListener.getResult(result);
    }
    public void setUpdateHeadImgResult(Result result){
        updateHeadImgListener.getResult(result);
    }
    public void setUpdateBgResult(Result result){
        updateBgListener.getResult(result);
    }

    public interface GetResultListener{
        void getResult(Result result);
    }


}
