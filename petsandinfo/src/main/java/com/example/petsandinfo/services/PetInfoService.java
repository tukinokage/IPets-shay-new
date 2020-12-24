package com.example.petsandinfo.services;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.PetInfoImg;
import com.shay.baselibrary.dto.PetIntroduce;

import java.util.HashMap;

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
}
