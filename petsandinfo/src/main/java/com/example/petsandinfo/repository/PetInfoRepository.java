package com.example.petsandinfo.repository;

import com.shay.baselibrary.dto.Result;

import java.util.Map;

public class PetInfoRepository {

    private static volatile PetInfoRepository instance;
    private PetIntroduceResultListener petIntroduceResultListener;
    synchronized public static PetInfoRepository getInstance(){
        if(instance == null){
            instance = new PetInfoRepository();
        }
        return instance;
    }


    public void loadPetIntroduce(Map<String, Object> params, PetIntroduceResultListener petIntroduceResultListener){



    }

    public interface PetIntroduceResultListener{
        void getResutl(Result result);
    }
}
