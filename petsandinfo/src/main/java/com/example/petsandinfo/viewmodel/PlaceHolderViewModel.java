package com.example.petsandinfo.viewmodel;

import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petsandinfo.model.entity.PetListLoadResult;
import com.shay.baselibrary.enums.UIFilterParams.RankTypeEnum;
import com.shay.baselibrary.enums.petInfo.FetchLevelEnum;
import com.shay.baselibrary.enums.petInfo.ShapeLevelEnum;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolderViewModel extends ViewModel {
    private MutableLiveData<List<String>> fetchLevelSelection = new MutableLiveData<>();
    private MutableLiveData<List<String>> shapeLevelSelection = new MutableLiveData<>();
    private MutableLiveData<List<String>> rankTypeSelection = new MutableLiveData<>();

    private LiveData<PetListLoadResult> petListLoadResultLiveData = new MutableLiveData<>();

    public LiveData<PetListLoadResult> getPetListLoadResultLiveData() {
        return petListLoadResultLiveData;
    }

    public LiveData<List<String>> getFetchLevelSelection() {
        return fetchLevelSelection;
    }

    public LiveData<List<String>> getShapeLevelSelection() {
        return shapeLevelSelection;
    }

    public LiveData<List<String>> getRankTypeSelection() {
        return rankTypeSelection;
    }

    public void LoadSelection(){
        List<String> fechLevelList = new ArrayList<>();
        List<String> shapeLevelList = new ArrayList<>();
        List<String> rankTypeList = new ArrayList<>();

        //默认
        fechLevelList.add("全部");
        shapeLevelList.add("全部");
        rankTypeList.add("全部");

        for (FetchLevelEnum e:
             FetchLevelEnum.values()) {
            fechLevelList.add(e.getChinese());
        }

        for (ShapeLevelEnum e:
                ShapeLevelEnum.values()) {
            shapeLevelList.add(e.name());
        }

        for (RankTypeEnum rankTypeEnum:
        RankTypeEnum.values()){
            rankTypeList.add(rankTypeEnum.getRankName());
        }

        /**
        * *
        * */
        fetchLevelSelection.setValue(fechLevelList);
        shapeLevelSelection.setValue(shapeLevelList);
        rankTypeSelection.setValue(rankTypeList);

    }

    public void LoadList(){

    }


}
