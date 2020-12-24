package com.example.petsandinfo.repository;

import com.example.petsandinfo.datasource.PetInfoDataSource;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.PetIntroduce;
import com.shay.baselibrary.dto.Result;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PetInfoRepository {

    private static volatile PetInfoRepository instance;
    private PetInfoDataSource petInfoDataSource;

    private PetIntroduceResultListener petIntroduceResultListener;
    private PetPicNameListResultListener petPicNameListResultListener;

    public PetInfoRepository(PetInfoDataSource petInfoDataSource) {
        this.petInfoDataSource = petInfoDataSource;
    }

    synchronized public static PetInfoRepository getInstance(PetInfoDataSource petInfoDataSource){
        if(instance == null){
            instance = new PetInfoRepository(petInfoDataSource);
        }
        return instance;
    }


    public void loadPetPicNameList(HashMap<String, Object> params, PetPicNameListResultListener petPicNameListResultListener){

        this.petPicNameListResultListener = petPicNameListResultListener;



    }

    public void loadPetIntroduce(HashMap<String, Object> params, PetIntroduceResultListener petIntroduceResultListener){

        this.petIntroduceResultListener = petIntroduceResultListener;
        petInfoDataSource.loadPetIntroduction(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<PetIntroduce>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<PetIntroduce> petIntroduceBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(petIntroduceBaseResponse);
                        setResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //回调监听
    private void setResult(Result result){
        petIntroduceResultListener.getResult(result);
    }

    private void setPicNameResult(Result result){

    }

    public interface PetIntroduceResultListener{
        void getResult(Result result);
    }

    public interface PetPicNameListResultListener{
        void getResult(Result result);
    }
}
