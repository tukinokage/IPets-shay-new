package com.example.petsandinfo.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petsandinfo.model.entity.Conditions.LoadPetHospitalCondition;
import com.example.petsandinfo.model.entity.Conditions.LoadPetIntroductionCondition;
import com.example.petsandinfo.model.entity.Conditions.LoadPetPicCondition;
import com.example.petsandinfo.model.entity.Conditions.LoadPetStoreCondition;
import com.example.petsandinfo.model.entity.LoadHospitalResult;
import com.example.petsandinfo.model.entity.LoadIntroduceResult;
import com.example.petsandinfo.model.entity.LoadPetPicNameResult;
import com.example.petsandinfo.model.entity.LoadStoreResult;
import com.example.petsandinfo.repository.PetInfoRepository;
import com.shay.baselibrary.factorys.AsyncTaskFactory;


public class PetInfoActivityViewModel extends ViewModel {
    private MutableLiveData<LoadIntroduceResult> introduceResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadStoreResult> loadStoreResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadHospitalResult> loadHospitalResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadPetPicNameResult> loadPetPicNameResultMutableLiveData = new MutableLiveData<>();

    private PetInfoRepository petInfoRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();

    public PetInfoActivityViewModel(PetInfoRepository petInfoRepository) {
        this.petInfoRepository = petInfoRepository;
    }
    /***************************ASYNCTASK CLASS**************************/
    private class LoadPetPicAsyncTask extends AsyncTask<LoadPetPicCondition, String, String>{


        @Override
        protected String doInBackground(LoadPetPicCondition... loadPetPicConditions) {
            LoadPetPicCondition loadPetPicCondition = loadPetPicConditions[0];
            return null;
        }
    }

    private class LoadPetIntroAsyncTask extends AsyncTask<LoadPetIntroductionCondition, String, String>{


        @Override
        protected String doInBackground(LoadPetIntroductionCondition... loadPetIntroductionConditions) {
            LoadPetIntroductionCondition loadPetIntroductionCondition = loadPetIntroductionConditions[0];
            return null;
        }
    }

    private class LoadPetHospitalAsyncTask extends AsyncTask<LoadPetHospitalCondition, String, String>{
        @Override
        protected String doInBackground(LoadPetHospitalCondition... LoadPetHospitalConditions) {
            LoadPetHospitalCondition petHospitalCondition = LoadPetHospitalConditions[0];
            return null;
        }
    }

    private class LoadPetStoreAsyncTask extends AsyncTask<LoadPetStoreCondition, String, String>{


        @Override
        protected String doInBackground(LoadPetStoreCondition... loadPetStoreConditions) {
            LoadPetStoreCondition loadPetStoreCondition = loadPetStoreConditions[0];
            return null;
        }
    }



    /***************************MUTABLELIVEDATA GETTER**************************/

    public MutableLiveData<LoadPetPicNameResult> getLoadPetPicNameResulticMutableLiveData() {
        return loadPetPicNameResultMutableLiveData;
    }

    public MutableLiveData<LoadIntroduceResult> getIntroduceResultMutableLiveData() {
        return introduceResultMutableLiveData;
    }

    public MutableLiveData<LoadStoreResult> getLoadStoreResultMutableLiveData() {
        return loadStoreResultMutableLiveData;
    }

    public MutableLiveData<LoadHospitalResult> getLoadHospitalResultMutableLiveData() {
        return loadHospitalResultMutableLiveData;
    }


    /*******************对外暴露的数据加载方法*************************/
    public void loadPetIntroduction(LoadPetIntroductionCondition condition){


    }

    public void loadPetPicNameList(LoadPetPicCondition condition){


    }

    public void loadPetHospitalList(LoadPetHospitalCondition condition){


    }

    public void loadPetStoreHospitalList(LoadPetStoreCondition condition){

    }





    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }




}
