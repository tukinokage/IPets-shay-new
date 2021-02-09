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
import com.example.petsandinfo.entity.PetInfoImg;
import com.example.petsandinfo.entity.params.CheckIsStarParam;
import com.example.petsandinfo.entity.params.StarPetParam;
import com.example.petsandinfo.entity.response.CheckIsStarResponse;
import com.example.petsandinfo.entity.result.CheckPetIsStarResult;
import com.example.petsandinfo.entity.result.LoadHospitalResult;
import com.example.petsandinfo.entity.result.LoadIntroduceResult;
import com.example.petsandinfo.entity.result.LoadPetPicNameResult;
import com.example.petsandinfo.entity.result.LoadStoreResult;
import com.example.petsandinfo.entity.result.StartPetResult;
import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.PetIntroduce;
import com.example.petsandinfo.model.Store;
import com.example.petsandinfo.repository.PetInfoRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.UserInfoUtil.*;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.HashMap;
import java.util.List;

public class PetInfoActivityViewModel extends ViewModel {
    private MutableLiveData<LoadIntroduceResult> introduceResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadStoreResult> loadStoreResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadHospitalResult> loadHospitalResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoadPetPicNameResult> loadPetPicNameResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<StartPetResult> startPetResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<StartPetResult> getStartPetResultMutableLiveData() {
        return startPetResultMutableLiveData;
    }


    public MutableLiveData<CheckPetIsStarResult> getCheckPetIsStarResultMutableLiveData() {
        return checkPetIsStarResultMutableLiveData;
    }

    private MutableLiveData<CheckPetIsStarResult> checkPetIsStarResultMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Drawable> bitmapMutableLiveData = new MutableLiveData<>();

    private PetInfoRepository petInfoRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private LoadPetPicAsyncTask loadPetPicAsyncTask;
    LoadPetHospitalAsyncTask loadPetHospitalAsyncTask;
    LoadPetIntroAsyncTask loadPetIntroAsyncTask;
    LoadPetStoreAsyncTask loadPetStoreAsyncTask;
    CheckStarAsyncTask checkStarAsyncTask;
    StarPetAsyncTask starPetAsyncTask;

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
            try {
                petInfoRepository.loadPetPicNameList(params, new PetInfoRepository.PetPicNameListResultListener() {
                    @Override
                    public void getResult(Result result) {
                        //
                        LoadPetPicNameResult petPicNameResult = new LoadPetPicNameResult();
                        if(result instanceof Result.Error){
                            Result.Error errorResult;
                            errorResult = (Result.Error) result;
                            petPicNameResult.setErrorMsg(errorResult.getErrorMsg());
                        }else if(result instanceof Result.Success){
                            Result.Success successResult = (Result.Success<PetInfoImg>) result;
                            PetInfoImg petInfoImg = (PetInfoImg) successResult.getData();
                            petPicNameResult.setData(petInfoImg.getImgList());
                        }

                        loadPetPicNameResultMutableLiveData.setValue(petPicNameResult);
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return "应用出错";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                loadPetPicNameResultMutableLiveData.setValue(new LoadPetPicNameResult(){{setErrorMsg(s);}});
            }
        }
    }

    private class LoadPetIntroAsyncTask extends AsyncTask<LoadPetIntroductionCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetIntroductionCondition... loadPetIntroductionConditions) {
            LoadPetIntroductionCondition loadPetIntroductionCondition = loadPetIntroductionConditions[0];
            try {
                Gson gson = new Gson();
                String json = gson.toJson(loadPetIntroductionCondition);

                HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                petInfoRepository.loadPetIntroduce(params, new PetInfoRepository.PetIntroduceResultListener() {
                    @Override
                    public void getResult(Result result) {
                        //rxjava回调在主线程
                        LoadIntroduceResult loadIntroduceResult = new LoadIntroduceResult();
                        if(result instanceof Result.Error){
                            Result.Error errorResult = (Result.Error) result;
                            loadIntroduceResult.setErrorMsg(errorResult.getErrorMsg());
                        }else if(result instanceof Result.Success){
                            Result.Success successResult = (Result.Success<PetIntroduce>) result;
                            loadIntroduceResult.setData((PetIntroduce) successResult.getData());
                        }

                        introduceResultMutableLiveData.setValue(loadIntroduceResult);
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return "应用出错";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                introduceResultMutableLiveData.setValue(new LoadIntroduceResult(){{setErrorMsg(s);}} );
            }
        }
    }

    private class LoadPetHospitalAsyncTask extends AsyncTask<LoadPetHospitalCondition, String, String>{
        @Override
        protected String doInBackground(LoadPetHospitalCondition... LoadPetHospitalConditions) {
            LoadPetHospitalCondition petHospitalCondition = LoadPetHospitalConditions[0];
            Gson gson = new Gson();

            try {
                String json = gson.toJson(petHospitalCondition);
                HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                petInfoRepository.loadPetHospital(params, new PetInfoRepository.PetHospitalResultListener() {
                    @Override
                    public void getResult(Result result) {
                        //rxjava回调在主线程
                        LoadHospitalResult loadHospitalResult = new LoadHospitalResult();
                        if(result instanceof Result.Error){
                            Result.Error errorResult = (Result.Error) result;
                            loadHospitalResult.setErrorMsg(errorResult.getErrorMsg());

                        }else if(result instanceof Result.Success){
                            Result.Success successResult = (Result.Success<List<Hospital>>) result;
                           loadHospitalResult.setData((List<Hospital>) successResult.getData());
                        }

                        loadHospitalResultMutableLiveData.setValue(loadHospitalResult);
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return "应用出错";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                loadHospitalResultMutableLiveData.setValue(new LoadHospitalResult(){{setErrorMsg(s);}});
            }
        }
    }

    private class LoadPetStoreAsyncTask extends AsyncTask<LoadPetStoreCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetStoreCondition... loadPetStoreConditions) {
            LoadPetStoreCondition loadPetStoreCondition = loadPetStoreConditions[0];
            Gson gson = new Gson();
            try{
                    String json = gson.toJson(loadPetStoreCondition);
                    HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                    petInfoRepository.loadPetStore(params, new PetInfoRepository.PetStoreResultListener() {
                        @Override
                        public void getResult(Result result) {
                            //rxjava回调在主线程
                            LoadStoreResult loadStoreResult = new LoadStoreResult();
                            if(result instanceof Result.Error){
                                Result.Error errorResult = (Result.Error) result;
                                loadStoreResult.setErrorMsg(errorResult.getErrorMsg());

                            }else if(result instanceof Result.Success){
                                Result.Success successResult = (Result.Success<List<Store>>) result;
                                 loadStoreResult.setData((List<Store>) successResult.getData());
                            }
                            loadStoreResultMutableLiveData.setValue(loadStoreResult);
                        }
                    });
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "应用出错";
                }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                loadStoreResultMutableLiveData.setValue(new LoadStoreResult(){{setErrorMsg(s);}});
            }
        }
    }

    private class CheckStarAsyncTask extends AsyncTask<CheckIsStarParam, String, String>{

        @Override
        protected String doInBackground(CheckIsStarParam... checkIsStarParams) {
            Gson gson = new Gson();
            try{
                    String json = gson.toJson(checkIsStarParams[0]);
                    HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                    petInfoRepository.checkStarPet(params, new PetInfoRepository.StarResultListener() {
                        @Override
                        public void getResult(Result result) {
                            //rxjava回调在主线程
                            CheckPetIsStarResult checkPetIsStarResult = new CheckPetIsStarResult();
                            if(result instanceof Result.Error){
                                Result.Error errorResult = (Result.Error) result;
                                checkPetIsStarResult.setErrorMsg(errorResult.getErrorMsg());

                            }else if(result instanceof Result.Success){
                                Result.Success successResult = (Result.Success<List<Store>>) result;
                                CheckIsStarResponse checkIsStarResponse = (CheckIsStarResponse) successResult.getData();
                                checkPetIsStarResult.setStar(checkIsStarResponse.isStar());
                            }
                            checkPetIsStarResultMutableLiveData.setValue(checkPetIsStarResult);
                        }
                    });
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "应用出错";
                }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                checkPetIsStarResultMutableLiveData.setValue(new CheckPetIsStarResult(){{setErrorMsg(s);}});
            }
        }
    }

    private class StarPetAsyncTask extends AsyncTask<StarPetParam, String, String>{

        @Override
        protected String doInBackground(StarPetParam... starPetParams) {
            Gson gson = new Gson();
            try{
                    String json = gson.toJson(starPetParams[0]);
                    HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                    petInfoRepository.starPet(params, result -> {
                        //rxjava回调在主线程
                        StartPetResult startPetResult = new StartPetResult();
                        if(result instanceof Result.Error){
                            Result.Error errorResult = (Result.Error) result;
                            startPetResult.setErrorMsg(errorResult.getErrorMsg());

                        }else if(result instanceof Result.Success){
                        }
                        startPetResultMutableLiveData.setValue(startPetResult);
                    });
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "应用出错";
                }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                startPetResultMutableLiveData.setValue(new StartPetResult(){{setErrorMsg(s);}});
            }
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

    public void checkIsStar(String petId) throws Exception {
        checkStarAsyncTask = (CheckStarAsyncTask) asyncTaskFactory.createAsyncTask(new CheckStarAsyncTask());
        CheckIsStarParam checkIsStarParam = new CheckIsStarParam();
        checkIsStarParam.setPetId(petId);
        checkIsStarParam.setUserId(UserInfoUtil.getUserId());
        checkStarAsyncTask.execute();
    }
    public void starPet(String petId) throws Exception {
       starPetAsyncTask = (StarPetAsyncTask) asyncTaskFactory.createAsyncTask(new StarPetAsyncTask());
       StarPetParam starPetParam = new StarPetParam();
       starPetParam.setPetId(petId);
       starPetParam.setUserId(UserInfoUtil.getUserId());
        starPetAsyncTask.execute(starPetParam);
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
