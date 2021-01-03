package com.shay.ipets.repository;

import com.shay.baselibrary.dto.Result;
import com.shay.ipets.datasource.MainDatasource;

public class MainRepository {
    private MainDatasource mainDatasource;
    private volatile static MainRepository instance;


    public MainRepository(MainDatasource mainDatasource) {
        this.mainDatasource = mainDatasource;
    }

    synchronized public static MainRepository getInstance(MainDatasource mainDatasource){
        if(instance == null){
            instance = new MainRepository(mainDatasource);
        }
        return instance;
    }

    private interface GetResultListener{
        void getResult(Result result);
    }
}
