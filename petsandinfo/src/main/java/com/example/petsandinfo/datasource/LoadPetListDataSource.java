package com.example.petsandinfo.datasource;

import com.example.petsandinfo.model.entity.Pet;
import com.example.petsandinfo.services.PetListService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class LoadPetListDataSource {

    public Observable<BaseResponse<List<Pet>>> loadPetList(HashMap<String, Object> paramsMap){
        Observable<BaseResponse<List<Pet>>> o = (Observable<BaseResponse<List<Pet>>>) new HttpUtil().getService(PetListService.class, UrlUtil.PET_URL.GET_PET_LIST_URL);

        return  o;
    }
}
