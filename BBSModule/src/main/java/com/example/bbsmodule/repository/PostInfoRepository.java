package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.shay.baselibrary.dto.Result;

public class PostInfoRepository {
    private BBSDatasource bbsDatasource;
    private volatile static PostInfoRepository instance;

    public PostInfoRepository(BBSDatasource bbsDatasource) {
        this.bbsDatasource = bbsDatasource;
    }

    synchronized public static PostInfoRepository getInstance(BBSDatasource bbsDatasource){
        if(instance == null){
            instance = new PostInfoRepository(bbsDatasource);
        }
        return instance;
    }

    private interface GetResultListener{
        void getResult(Result result);
    }

}
