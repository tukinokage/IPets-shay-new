package com.shay.ipets.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shay.ipets.datasource.DailyRecordDatasource;
import com.shay.ipets.datasource.MainDatasource;
import com.shay.ipets.repository.DaliyRecordRepository;
import com.shay.ipets.repository.MainRepository;

public class DaliyRecordViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DaliRecordModel.class)){
            return (T) new DaliRecordModel(DaliyRecordRepository.getInstance(new DailyRecordDatasource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
