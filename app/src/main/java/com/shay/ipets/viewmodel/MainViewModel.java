package com.shay.ipets.viewmodel;

import androidx.lifecycle.ViewModel;

import com.shay.ipets.repository.MainRepository;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;

    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }
}
