package com.shay.ipets.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shay.ipets.datasource.MainDatasource;
import com.shay.ipets.datasource.PostDatasource;
import com.shay.ipets.repository.MainRepository;
import com.shay.ipets.repository.PostRepository;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PostViewModel.class)){
            return (T) new PostViewModel(PostRepository.getInstance(new PostDatasource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
