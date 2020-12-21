package com.example.petsandinfo.services;

import com.example.petsandinfo.model.entity.Pet;
import com.shay.baselibrary.dto.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface   PetListService {
    @POST
    @FormUrlEncoded
    Observable<BaseResponse<List<Pet>>> loadPetsListData(@FieldMap HashMap<String, Object> map);
}
