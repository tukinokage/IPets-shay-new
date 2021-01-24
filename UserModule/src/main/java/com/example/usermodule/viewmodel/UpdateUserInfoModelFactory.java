package com.example.usermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.usermodule.datasource.UpdateInfoDataSource;
import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.repository.UpdateUserInfoRepository;
import com.example.usermodule.repository.UserInfoRepository;

public class UpdateUserInfoModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserInfoViewModel.class)){
            return (T) new UserInfoViewModel(UpdateUserInfoRepository.getInstance(new UpdateInfoDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
