package com.example.bbsmodule.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.repository.BBSRepository;

public class PostInfoViewModel extends ViewModel {
    private BBSRepository bbsRepository;

    public PostInfoViewModel(BBSRepository bbsRepository) {
        this.bbsRepository = bbsRepository;
    }
}
