package com.example.petsandinfo.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.petsandinfo.datasource.LoadPetListDataSource;
import com.example.petsandinfo.repository.LoadPetListRepository;

public class PlaceHolderViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaceHolderViewModel.class)){

            return (T) new PlaceHolderViewModel(LoadPetListRepository.getInstance(new LoadPetListDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
