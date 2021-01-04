package com.example.petsandinfo.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.petsandinfo.entity.Conditions.LoadPetHospitalCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetIntroductionCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetPicCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetStoreCondition;
import com.example.petsandinfo.entity.result.LoadHospitalResult;
import com.example.petsandinfo.entity.result.LoadIntroduceResult;
import com.example.petsandinfo.entity.result.LoadPetPicNameResult;
import com.example.petsandinfo.entity.result.LoadStoreResult;
import com.example.petsandinfo.repository.PetInfoRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.HashMap;

public class PetInfoActivityViewModel extends ViewModel {
    private MutableLiveData<LoadIntroduceResult> introduceResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadStoreResult> loadStoreResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadHospitalResult> loadHospitalResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadPetPicNameResult> loadPetPicNameResultMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Drawable> bitmapMutableLiveData = new MutableLiveData<>();

    private PetInfoRepository petInfoRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private LoadPetPicAsyncTask loadPetPicAsyncTask;
    LoadPetHospitalAsyncTask loadPetHospitalAsyncTask;
    LoadPetIntroAsyncTask loadPetIntroAsyncTask;
    LoadPetStoreAsyncTask loadPetStoreAsyncTask;

    public PetInfoActivityViewModel(PetInfoRepository petInfoRepository) {
        this.petInfoRepository = petInfoRepository;
    }
    /***************************ASYNCTASK CLASS**************************/
    private class LoadPetPicAsyncTask extends AsyncTask<LoadPetPicCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetPicCondition... loadPetPicConditions) {
            LoadPetPicCondition loadPetPicCondition = loadPetPicConditions[0];
            Gson gson = new Gson();
            String json = gson.toJson(loadPetPicCondition);
            HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
            return null;
        }
    }

    private class LoadPetIntroAsyncTask extends AsyncTask<LoadPetIntroductionCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetIntroductionCondition... loadPetIntroductionConditions) {
            LoadPetIntroductionCondition loadPetIntroductionCondition = loadPetIntroductionConditions[0];
            Gson gson = new Gson();
            String json = gson.toJson(loadPetIntroductionCondition);
            HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());

            return null;
        }
    }

    private class LoadPetHospitalAsyncTask extends AsyncTask<LoadPetHospitalCondition, String, String>{
        @Override
        protected String doInBackground(LoadPetHospitalCondition... LoadPetHospitalConditions) {
            LoadPetHospitalCondition petHospitalCondition = LoadPetHospitalConditions[0];
            Gson gson = new Gson();
            String json = gson.toJson(petHospitalCondition);
            HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
            petInfoRepository.loadPetHospital(params, new PetInfoRepository.PetHospitalResultListener() {
                @Override
                public void getResult(Result result) {

                }
            });
            return null;
        }
    }

    private class LoadPetStoreAsyncTask extends AsyncTask<LoadPetStoreCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetStoreCondition... loadPetStoreConditions) {
            LoadPetStoreCondition loadPetStoreCondition = loadPetStoreConditions[0];
            Gson gson = new Gson();
            String json = gson.toJson(loadPetStoreCondition);
            HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());

            return null;
        }
    }



    /***************************MUTABLELIVEDATA GETTER**************************/
    public MutableLiveData<Drawable> getBitmapMutableLiveData() {
        return bitmapMutableLiveData;
    }
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
       loadPetIntroAsyncTask = (LoadPetIntroAsyncTask) asyncTaskFactory.createAsyncTask(new LoadPetIntroAsyncTask());
       loadPetIntroAsyncTask.execute(condition);

    }

    public void loadPetPicNameList(LoadPetPicCondition condition){
        loadPetPicAsyncTask = (LoadPetPicAsyncTask) asyncTaskFactory.createAsyncTask(new LoadPetPicAsyncTask());
        loadPetPicAsyncTask.execute(condition);

    }

    public void loadPetHospitalList(LoadPetHospitalCondition condition){

        loadPetHospitalAsyncTask = (LoadPetHospitalAsyncTask) asyncTaskFactory.createAsyncTask(new LoadPetHospitalAsyncTask());
        loadPetHospitalAsyncTask.execute(condition);
    }

    public void loadPetStoreList(LoadPetStoreCondition condition){
        loadPetStoreAsyncTask = (LoadPetStoreAsyncTask) asyncTaskFactory.createAsyncTask(new LoadPetStoreAsyncTask());
        loadPetStoreAsyncTask.execute(condition);
    }

    //单独图片单独获取
    public void loadHeadPic(String picName){
         Glide.with(AppContext.getContext()).load(UrlUtil.PET_PIC_URL.HEAD_ICON_URL + picName).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                bitmapMutableLiveData.setValue(resource);
            }
        });
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
