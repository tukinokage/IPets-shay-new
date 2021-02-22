package com.example.bbsmodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.datasource.PostInfoDatasource;
import com.example.bbsmodule.repository.BBSRepository;
import com.example.bbsmodule.repository.PostInfoRepository;

public class PostInfoModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PostInfoViewModel.class)){
            return (T) new PostInfoViewModel(PostInfoRepository.getInstance(new PostInfoDatasource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
