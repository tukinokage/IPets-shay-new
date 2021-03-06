package com.example.petsandinfo.datasource;

import com.example.petsandinfo.entity.response.CheckIsStarResponse;
import com.example.petsandinfo.entity.response.StarPetResponse;
import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.Store;
import com.example.petsandinfo.services.PetInfoService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.example.petsandinfo.entity.PetInfoImg;
import com.example.petsandinfo.model.PetIntroduce;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


public class PetInfoDataSource {
    //pet介绍
    public Observable<BaseResponse<PetIntroduce>> loadPetIntroduction(HashMap<String, Object> paramsMap){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.getPetIntroduction(paramsMap);
    }

    //图片名字
    public Observable<BaseResponse<PetInfoImg>> loadPetImgs(HashMap<String, Object> paramsMasp){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.getPetPicNameList(paramsMasp);
    }

    //Stores
    public Observable<BaseResponse<List<Store>>> loadPetStore(HashMap<String, Object> paramsMasp){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.getPetStoreList(paramsMasp);
    }

    //Hospitals
    public Observable<BaseResponse<List<Hospital>>> loadPetHospital(HashMap<String, Object> paramsMasp){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.getPetHospitalList(paramsMasp);
    }

    //star
    public Observable<BaseResponse<StarPetResponse>> starPet(HashMap<String, Object> paramsMasp){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.starPet(paramsMasp);
    }

    //checkstar
    public Observable<BaseResponse<CheckIsStarResponse>> checkPet(HashMap<String, Object> paramsMasp){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.BASE_URL.BASE_URL);
        return petInfoService.checkStar(paramsMasp);
    }

}
