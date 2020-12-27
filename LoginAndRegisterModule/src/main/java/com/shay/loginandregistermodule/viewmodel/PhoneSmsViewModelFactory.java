package com.shay.loginandregistermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shay.loginandregistermodule.data.datasource.LoginDataSource;
import com.shay.loginandregistermodule.data.datasource.PhoneSmsDataSource;
import com.shay.loginandregistermodule.data.repository.LoginRepository;
import com.shay.loginandregistermodule.data.repository.PhoneSmsRepository;

public class PhoneSmsViewModelFactory implements ViewModelProvider.Factory {


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PhoneSmsViewModel.class)){
            return (T) new PhoneSmsViewModel(PhoneSmsRepository.getInstance(new PhoneSmsDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
