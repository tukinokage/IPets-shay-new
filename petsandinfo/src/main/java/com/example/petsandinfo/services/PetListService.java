package com.example.petsandinfo.services;

import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.response.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PetListService {
    @POST(UrlUtil.PET_URL.GET_PET_LIST_URL)
    @FormUrlEncoded
    Observable<BaseResponse<List<Pet>>> loadPetsListData(@FieldMap HashMap<String, Object> map);
}
