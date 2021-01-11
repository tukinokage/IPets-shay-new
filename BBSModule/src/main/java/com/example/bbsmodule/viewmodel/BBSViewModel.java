package com.example.bbsmodule.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.repository.BBSRepository;

public class BBSViewModel extends ViewModel {
    private BBSRepository bbsRepository;

    public BBSViewModel(BBSRepository bbsRepository) {
        this.bbsRepository = bbsRepository;
    }
}
