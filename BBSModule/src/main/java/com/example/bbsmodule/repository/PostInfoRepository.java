package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.datasource.PostInfoDatasource;
import com.shay.baselibrary.dto.Result;

public class PostInfoRepository {
    private PostInfoDatasource postInfoDatasource;
    private volatile static PostInfoRepository instance;

    public PostInfoRepository(PostInfoDatasource postInfoDatasource) {
        this.postInfoDatasource = postInfoDatasource;
    }

    synchronized public static PostInfoRepository getInstance(PostInfoDatasource postInfoDatasource){
        if(instance == null){
            instance = new PostInfoRepository(postInfoDatasource);
        }
        return instance;
    }

    private interface GetResultListener{
        void getResult(Result result);
    }

}
