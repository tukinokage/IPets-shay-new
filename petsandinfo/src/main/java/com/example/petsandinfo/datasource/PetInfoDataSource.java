package com.example.petsandinfo.datasource;

import com.example.petsandinfo.services.PetInfoService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.PetIntroduce;

import java.util.HashMap;

import io.reactivex.Observable;


public class PetInfoDataSource {
    public Observable<BaseResponse<PetIntroduce>> loadPetIntroduction(HashMap<String, Object> paramsMap){
        PetInfoService petInfoService = new HttpUtil().getService(PetInfoService.class, UrlUtil.PET_URL.GET_PET_LIST_URL);

        return petInfoService.getPetIntroduction(paramsMap);
    }
}
