package com.shay.ipets.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shay.ipets.datasource.MainDatasource;
import com.shay.ipets.repository.MainRepository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){

            return (T) new MainViewModel(MainRepository.getInstance(new MainDatasource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
