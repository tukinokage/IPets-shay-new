package com.example.petsandinfo.services;

import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.Store;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.example.petsandinfo.entity.PetInfoImg;
import com.example.petsandinfo.model.PetIntroduce;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PetInfoService {

    @POST(UrlUtil.PET_URL.GET_PET_INTRODUCE_URL)
    @FormUrlEncoded
    Observable<BaseResponse<PetIntroduce>> getPetIntroduction(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.PET_URL.GET_PET_IMAGE_URL)
    @FormUrlEncoded
    Observable<BaseResponse<PetInfoImg>> getPetPicNameList(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.PET_URL.GET_LOAD_HOSPITAL_URL)
    @FormUrlEncoded
    Observable<BaseResponse<List<Hospital>>> getPetHospitalList(@FieldMap HashMap<String, Object> map);

    @POST(UrlUtil.PET_URL.GET_LOAD_STORE_URL)
    @FormUrlEncoded
    Observable<BaseResponse<List<Store>>> getPetStoreList(@FieldMap HashMap<String, Object> map);

}
