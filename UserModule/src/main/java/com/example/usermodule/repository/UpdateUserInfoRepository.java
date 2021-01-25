package com.example.usermodule.repository;

import com.example.usermodule.datasource.UpdateInfoDataSource;
import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.params.UpdateBgParam;
import com.example.usermodule.entity.params.UpdateHeadImgParam;
import com.example.usermodule.entity.params.UpdateUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.google.gson.Gson;
import com.shay.baselibrary.FileTransfromUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class UpdateUserInfoRepository {
    private UpdateInfoDataSource updateInfoDataSource;
    private GetResultListener updateUserInfoListener;
    private GetResultListener updateHeadImgListener;
    private GetResultListener updateBgListener;
    private volatile static UpdateUserInfoRepository instance;

    public UpdateUserInfoRepository(UpdateInfoDataSource updateInfoDataSource) {
        this.updateInfoDataSource = updateInfoDataSource;
    }

    synchronized public static UpdateUserInfoRepository getInstance(UpdateInfoDataSource updateInfoDataSource){
        if(instance == null){
            instance = new UpdateUserInfoRepository(updateInfoDataSource);
        }
        return instance;
    }

    //info
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
                        setUpdateUserInfoResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setUpdateUserInfoResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //headimg
    public void updateHeadImg(UpdateHeadImgParam updateHeadImgParam, GetResultListener updateHeadImgListener) throws Exception{
        this.updateHeadImgListener = updateHeadImgListener;

        File file = new File(updateHeadImgParam.getUri());

        updateHeadImgParam.setUserToken(UserInfoUtil.getUserToken());

        String json = new Gson().toJson(updateHeadImgParam);
        byte[] bs = FileTransfromUtil.File2byte(file);
        ResponseBody responseText = ResponseBody.create(MediaType.parse("text/plain"), json);
        ResponseBody responsePic = ResponseBody.create(MediaType.parse("image/*"), bs);
        HashMap<String, ResponseBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);
        hashMap.put("file", responsePic);

        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(hashMap);
        updateInfoDataSource.updateUserHeadIcon(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UpdateUserInfoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UpdateUserInfoResponse> updateUserInfoResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(updateUserInfoResponse);
                        setUpdateHeadImgResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setUpdateHeadImgResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //bg
    public void updateBg(UpdateBgParam updateBgParam, GetResultListener updateBgListener) throws Exception{
        this.updateBgListener = updateBgListener;

        File file = new File(updateBgParam.getUri());

        updateBgParam.setUserToken(UserInfoUtil.getUserToken());

        String json = new Gson().toJson(updateBgParam);
        byte[] bs = FileTransfromUtil.File2byte(file);
        ResponseBody responseText = ResponseBody.create(MediaType.parse("text/plain"), json);
        ResponseBody responsePic = ResponseBody.create(MediaType.parse("image/*"), bs);
        HashMap<String, ResponseBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);
        hashMap.put("file", responsePic);

        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(hashMap);
        updateInfoDataSource.updateUserBg(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UpdateUserInfoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UpdateUserInfoResponse> updateUserInfoResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(updateUserInfoResponse);
                        setUpdateBgResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setUpdateBgResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void setUpdateUserInfoResult(Result result){
        updateUserInfoListener.getResult(result);
    }
    private void setUpdateHeadImgResult(Result result){
        updateHeadImgListener.getResult(result);
    }
    private void setUpdateBgResult(Result result){
        updateBgListener.getResult(result);
    }

    public interface GetResultListener{
        void getResult(Result result);
    }


}