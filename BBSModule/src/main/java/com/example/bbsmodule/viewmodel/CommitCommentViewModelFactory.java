package com.example.bbsmodule.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.datasource.CommitCommentDataSource;
import com.example.bbsmodule.repository.BBSRepository;
import com.example.bbsmodule.repository.CommitCommentRepository;

public class CommitCommentViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CommitCommentViewModel.class)){
            return (T) new CommitCommentViewModel(CommitCommentRepository.getInstance(new CommitCommentDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
