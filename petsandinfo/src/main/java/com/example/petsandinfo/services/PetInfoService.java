package com.example.petsandinfo.services;

import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.Store;
import com.shay.baselibrary.dto.BaseResponse;
import com.example.petsandinfo.entity.PetInfoImg;
import com.example.petsandinfo.model.PetIntroduce;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PetInfoService {

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<PetIntroduce>> getPetIntroduction(@FieldMap HashMap<String, Object> map);

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<PetInfoImg>> getPetPicNameList(@FieldMap HashMap<String, Object> map);

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<List<Hospital>>> getPetHospitalList(@FieldMap HashMap<String, Object> map);

    @POST
    @FormUrlEncoded
    Observable<BaseResponse<List<Store>>> getPetStoreList(@FieldMap HashMap<String, Object> map);


}
