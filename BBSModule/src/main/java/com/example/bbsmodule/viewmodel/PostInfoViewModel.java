package com.example.bbsmodule.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.adapter.PostInfoListAdapter;
import com.example.bbsmodule.repository.BBSRepository;
import com.example.bbsmodule.repository.PostInfoRepository;

public class PostInfoViewModel extends ViewModel {
    private PostInfoRepository postInfoRepository;

    public PostInfoViewModel(PostInfoRepository postInfoRepository) {
        this.postInfoRepository = postInfoRepository;
    }
}
