package com.example.petsandinfo.datasource;

import com.shay.baselibrary.dto.Pet;
import com.example.petsandinfo.services.PetListService;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observable;

public class LoadPetListDataSource {
    public Observable<BaseResponse<List<Pet>>> loadPetList(HashMap<String, Object> paramsMap){
        PetListService petListService =  new HttpUtil()
                .getService(PetListService.class, UrlUtil.PET_URL.GET_PET_LIST_URL);
        Observable<BaseResponse<List<Pet>>> result = petListService.loadPetsListData(paramsMap);
        return  result;
    }
}
