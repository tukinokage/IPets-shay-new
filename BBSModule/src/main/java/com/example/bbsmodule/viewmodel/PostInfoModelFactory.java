package com.example.bbsmodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.repository.BBSRepository;

public class PostInfoModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BBSViewModel.class)){
            return (T) new BBSViewModel(BBSRepository.getInstance(new BBSDatasource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
