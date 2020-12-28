package com.shay.loginandregistermodule.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shay.loginandregistermodule.data.entity.SetPwResult;

public class SetPasswordViewModel extends ViewModel {
    private MutableLiveData<SetPwResult> setPwResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<SetPwResult> getSetPwResultMutableLiveData() {
        return setPwResultMutableLiveData;
    }

}
