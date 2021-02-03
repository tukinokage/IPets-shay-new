package com.shay.loginandregistermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shay.loginandregistermodule.data.datasource.SetPwDataSource;
import com.shay.loginandregistermodule.data.repository.SetPwRepository;

public class SetPasswordFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SetPasswordViewModel.class)){
            return (T) new SetPasswordViewModel(SetPwRepository.getInstance(new SetPwDataSource()));
        }else {
            throw new IllegalStateException("no exist view model class");
        }
    }
}
