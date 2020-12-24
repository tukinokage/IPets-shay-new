package com.example.petsandinfo.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.petsandinfo.datasource.LoadPetListDataSource;
import com.example.petsandinfo.datasource.PetInfoDataSource;
import com.example.petsandinfo.repository.LoadPetListRepository;
import com.example.petsandinfo.repository.PetInfoRepository;

public class PetInfoActivityViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PetInfoActivityViewModel.class)){

            return (T) new PetInfoActivityViewModel(PetInfoRepository.getInstance(new PetInfoDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
