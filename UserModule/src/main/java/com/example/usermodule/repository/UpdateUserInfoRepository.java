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
import com.shay.baselibrary.UserInfoUtil.*;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        updateUserInfoParam.setUserId(UserInfoUtil.getUserId());
        updateUserInfoParam.setUserToken(UserInfoUtil.getUserToken());
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
        updateHeadImgParam.setUserId(UserInfoUtil.getUserId());

        String json = new Gson().toJson(updateHeadImgParam);
        RequestBody responseText = RequestBody.create(MediaType.parse("text/plain"), json);
        RequestBody responsePic = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), responsePic);
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);

        updateInfoDataSource.updateUserHeadIcon(hashMap, part)
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
        updateBgParam.setUserId(UserInfoUtil.getUserId());

        String json = new Gson().toJson(updateBgParam);
        RequestBody responseText = RequestBody.create(MediaType.parse("text/plain"), json);
        RequestBody responsePic = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), responsePic);
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);
        updateInfoDataSource.updateUserBg(hashMap, part)
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
