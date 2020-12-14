package com.example.petsandinfo.repository;

import com.example.petsandinfo.datasource.LoadPetListDataSource;
import com.shay.baselibrary.dto.Result;

import java.util.Map;

public class LoadPetListRepository {

    private volatile static LoadPetListRepository instance;
    private LoadPetListDataSource petListDataSource;
    private GetResultListener getResultListener;
    public LoadPetListRepository(LoadPetListDataSource loadPetListDataSource) {
        this.petListDataSource = loadPetListDataSource;
    }

    synchronized public static LoadPetListRepository getInstance(LoadPetListDataSource loadPetListDataSource) {
        if(instance == null){
            instance = new LoadPetListRepository(loadPetListDataSource);
        }

        return instance;
    }

    public void loadPetList(Map<String, Object> params, GetResultListener getResultListener){
        this.getResultListener = getResultListener;
    }

    private void setResult(Result result){

        getResultListener.getResult(result);
    }
    public interface GetResultListener{
        void getResult(Result result);
    }


}
