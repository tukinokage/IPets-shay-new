package com.example.usermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.usermodule.datasource.UpdateInfoDataSource;
import com.example.usermodule.repository.UpdateUserInfoRepository;

public class UpdateUserInfoModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpdateInfoViewModel.class)){
            return (T) new UpdateInfoViewModel(UpdateUserInfoRepository.getInstance(new UpdateInfoDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
