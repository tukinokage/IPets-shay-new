package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.shay.baselibrary.dto.Result;

public class BBSRepository {
    private BBSDatasource bbsDatasource;
    private volatile static BBSRepository instance;


    public BBSRepository(BBSDatasource bbsDatasource) {
        this.bbsDatasource = bbsDatasource;
    }

    synchronized public static BBSRepository getInstance(BBSDatasource bbsDatasource){
        if(instance == null){
            instance = new BBSRepository(bbsDatasource);
        }
        return instance;
    }

    private interface GetResultListener{
        void getResult(Result result);
    }
}
