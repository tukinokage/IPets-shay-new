package com.example.petsandinfo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petsandinfo.model.entity.LoadHospitalResult;
import com.example.petsandinfo.model.entity.LoadIntroduceResult;
import com.example.petsandinfo.model.entity.LoadStoreResult;

public class PetInfoActivityViewModel extends ViewModel {
    private MutableLiveData<LoadIntroduceResult> introduceResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadStoreResult> loadStoreResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadHospitalResult> loadHospitalResultMutableLiveData = new MutableLiveData<>();


}
