package com.example.usermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.repository.UserInfoRepository;

public class GetDailyViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GetDailyRecordListViewModel.class)){
            return (T) new GetDailyRecordListViewModel(UserInfoRepository.getInstance(new UserDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
