package com.example.petsandinfo.repository;

import com.example.petsandinfo.datasource.PetInfoDataSource;
import com.example.petsandinfo.model.entity.Hospital;
import com.example.petsandinfo.model.entity.Store;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.PetInfoImg;
import com.shay.baselibrary.dto.PetIntroduce;
import com.shay.baselibrary.dto.Result;

import java.util.HashMap;
import java.util.List;
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
    private PetHospitalResultListener petHospitalResultListener;
    private PetStoreResultListener petStoreResultListener;

    public PetInfoRepository(PetInfoDataSource petInfoDataSource) {
        this.petInfoDataSource = petInfoDataSource;
    }

    synchronized public static PetInfoRepository getInstance(PetInfoDataSource petInfoDataSource){
        if(instance == null){
            instance = new PetInfoRepository(petInfoDataSource);
        }
        return instance;
    }


    public void loadPetHospital(HashMap<String, Object> params, PetHospitalResultListener petHospitalResultListener){
        this.petHospitalResultListener = petHospitalResultListener;
        petInfoDataSource.loadPetHospital(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse<List<Hospital>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<Hospital>> listBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(listBaseResponse);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadPetStore(HashMap<String, Object> params, PetStoreResultListener petStoreResultListener){
        this.petStoreResultListener = petStoreResultListener;
        petInfoDataSource.loadPetStore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse<List<Store>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<Store>> listBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(listBaseResponse);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadPetPicNameList(HashMap<String, Object> params, PetPicNameListResultListener petPicNameListResultListener){

        this.petPicNameListResultListener = petPicNameListResultListener;
        petInfoDataSource.loadPetImgs(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse<PetInfoImg>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<PetInfoImg> petInfoImgBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(petInfoImgBaseResponse);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setPicNameResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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

    //回调监听,可做聚合操作
    private void setResult(Result result){
        petIntroduceResultListener.getResult(result);
    }

    private void setPicNameResult(Result result){
        petPicNameListResultListener.getResult(result);
    }

    private void setPetHospitalResult(Result result){
        petHospitalResultListener.getResult(result);
    }

    private void setPetStoreResult(Result result){
        petStoreResultListener.getResult(result);
    }

    public interface PetIntroduceResultListener{
        void getResult(Result result);
    }

    public interface PetPicNameListResultListener{
        void getResult(Result result);
    }

    public interface PetHospitalResultListener{
        void getResult(Result result);
    }

    public interface PetStoreResultListener{
        void getResult(Result result);
    }
}
