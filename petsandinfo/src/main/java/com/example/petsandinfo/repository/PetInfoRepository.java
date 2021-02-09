package com.example.petsandinfo.repository;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.petsandinfo.datasource.PetInfoDataSource;
import com.example.petsandinfo.entity.response.CheckIsStarResponse;
import com.example.petsandinfo.entity.response.StarPetResponse;
import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.Store;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.example.petsandinfo.entity.PetInfoImg;
import com.example.petsandinfo.model.PetIntroduce;
import com.shay.baselibrary.dto.Result;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
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
    private StarResultListener starPetResultListener;
    private StarResultListener checkStarPetResultListener;

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
                        setPetHospitalResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setPetHospitalResult(result);
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
                        setPetStoreResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setPetStoreResult(result);
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

    public void starPet(HashMap<String, Object> params, StarResultListener starPetResultListener){

        this.starPetResultListener = starPetResultListener;
        petInfoDataSource.starPet(params)
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

    public void checkStarPet(HashMap<String, Object> params, StarResultListener checkStarPetResultListener){

        this.checkStarPetResultListener = checkStarPetResultListener;
        petInfoDataSource.checkPet(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<CheckIsStarResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CheckIsStarResponse> checkIsStarResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(checkIsStarResponseBaseResponse);
                        setCheckStarPetResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setCheckStarPetResult(result);
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

    private void setStarPetResult(Result result){
        starPetResultListener.getResult(result);
    }

    private void setCheckStarPetResult(Result result){
        checkStarPetResultListener.getResult(result);
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

    public interface StarResultListener{
        void getResult(Result result);
    }
}
