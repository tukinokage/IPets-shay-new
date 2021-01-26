package com.example.usermodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.usermodule.datasource.UserDataSource;
import com.example.usermodule.repository.UserInfoRepository;

public class GetUserCommentViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GetUserCommentListViewModel.class)){
            return (T) new GetUserCommentListViewModel(UserInfoRepository.getInstance(new UserDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
