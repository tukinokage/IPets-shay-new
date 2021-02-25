package com.example.petsandinfo.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petsandinfo.entity.Conditions.LoadPetCondition;
import com.shay.baselibrary.dto.Pet;
import com.example.petsandinfo.entity.result.PetListLoadResult;
import com.example.petsandinfo.repository.LoadPetListRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.enums.UIFilterParams.RankTypeEnum;
import com.shay.baselibrary.enums.petInfo.FetchLevelEnum;
import com.shay.baselibrary.enums.petInfo.ShapeLevelEnum;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceHolderViewModel extends ViewModel {
    private MutableLiveData<List<String>> fetchLevelSelection = new MutableLiveData<>();
    private MutableLiveData<List<String>> shapeLevelSelection = new MutableLiveData<>();
    private MutableLiveData<List<String>> rankTypeSelection = new MutableLiveData<>();

    private MutableLiveData<PetListLoadResult> petListLoadResultLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Pet>> petListLiveData = new MutableLiveData<>();

    private LoadPetListRepository loadPetListRepository;

    private LoadPetListAsyncTask loadPetListAsyncTask;
    private LoadMorePetListAsyncTask morePetListAsyncTask;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private final int perPagerCount = 15;
    private int currentPagerNum = 1;

    private List<Pet> currentPetList = new ArrayList<>();

    public class LoadPetListAsyncTask extends AsyncTask<LoadPetCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetCondition... loadPetConditions) {
            LoadPetCondition loadPetCondition = loadPetConditions[0];
            String json = new Gson().toJson(loadPetCondition);
            HashMap<String, Object> paramsMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());

            loadPetListRepository.loadPetList(paramsMap, result -> {

                //rxjava回调在主线程
                if(result instanceof Result.Error){
                    Result.Error errorResult = (Result.Error) result;
                    PetListLoadResult petListLoadResult = new PetListLoadResult();
                    petListLoadResult.setErrorMsg(errorResult.getError().getMessage());
                    petListLoadResultLiveData.setValue(petListLoadResult);
                }else if(result instanceof Result.Success){
                    currentPagerNum = 2;
                    Result.Success successResult = (Result.Success<List<Pet>>) result;
                    currentPetList.clear();
                    currentPetList.addAll((List<Pet>) successResult.getData());
                    PetListLoadResult petListLoadResult = new PetListLoadResult();
                    petListLoadResultLiveData.setValue(petListLoadResult);
                    petListLiveData.setValue(currentPetList);
                }

            });
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class LoadMorePetListAsyncTask extends AsyncTask<LoadPetCondition, String, String>{

        @Override
        protected String doInBackground(LoadPetCondition... loadPetConditions) {
            LoadPetCondition loadPetCondition = loadPetConditions[0];
            String json = new Gson().toJson(loadPetCondition);
            HashMap<String, Object> paramsMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());

            loadPetListRepository.loadPetList(paramsMap, result -> {

                //rxjava回调在主线程
                if(result instanceof Result.Error){
                    Result.Error errorResult = (Result.Error) result;
                    PetListLoadResult petListLoadResult = new PetListLoadResult();
                    petListLoadResult.setErrorMsg(errorResult.getError().getMessage());
                    petListLoadResultLiveData.setValue(petListLoadResult);
                }else if(result instanceof Result.Success){
                    currentPagerNum ++;
                    Result.Success successResult = (Result.Success<List<Pet>>) result;
                    currentPetList.addAll((List<Pet>) successResult.getData());
                    petListLiveData.setValue(currentPetList);
                    PetListLoadResult petListLoadResult = new PetListLoadResult();
                    petListLoadResultLiveData.setValue(petListLoadResult);
                }

            });
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public PlaceHolderViewModel(LoadPetListRepository loadPetListRepository) {
        this.loadPetListRepository = loadPetListRepository;
    }

    public MutableLiveData<List<Pet>> getpetListLiveData() {
        return petListLiveData;
    }
    public LiveData<PetListLoadResult> getPetListLoadResultLiveData() {
        return petListLoadResultLiveData;
    }

    public LiveData<List<String>> getFetchLevelSelection() {
        return fetchLevelSelection;
    }

    public LiveData<List<String>> getShapeLevelSelection() {
        return shapeLevelSelection;
    }

    public LiveData<List<String>> getRankTypeSelection() {
        return rankTypeSelection;
    }

    public void LoadSelection(){
        List<String> fechLevelList = new ArrayList<>();
        List<String> shapeLevelList = new ArrayList<>();
        List<String> rankTypeList = new ArrayList<>();

        //默认
        fechLevelList.add("全部");
        shapeLevelList.add("全部");

        for (FetchLevelEnum e:
             FetchLevelEnum.values()) {
            fechLevelList.add(e.getChinese());
        }

        for (ShapeLevelEnum e:
                ShapeLevelEnum.values()) {
            shapeLevelList.add(e.getChinese());
        }

        for (RankTypeEnum rankTypeEnum:
        RankTypeEnum.values()){
            rankTypeList.add(rankTypeEnum.getRankName());
        }

        /**
        * *
        * */
        fetchLevelSelection.setValue(fechLevelList);
        shapeLevelSelection.setValue(shapeLevelList);
        rankTypeSelection.setValue(rankTypeList);
    }

    public void loadList(int shapeLevel, int fetchLevel, int rankType, int petClass){

        LoadPetCondition loadPetCondition = new LoadPetCondition();
        loadPetCondition.setFetchLevel(fetchLevel);
        loadPetCondition.setShapeLevel(shapeLevel);
        loadPetCondition.setRankType(rankType);
        loadPetCondition.setPetClass(petClass);
        loadPetCondition.setCurrentPageNum(1);
        loadPetCondition.setPerPageCount(perPagerCount);
        loadPetListAsyncTask = (LoadPetListAsyncTask) asyncTaskFactory.createAsyncTask(new LoadPetListAsyncTask());
        loadPetListAsyncTask.execute(loadPetCondition);
    }

    public void loadMore(int shapeLevel, int fetchLevel, int rankType, int petClass){

        LoadPetCondition loadPetCondition = new LoadPetCondition();
        loadPetCondition.setFetchLevel(fetchLevel);
        loadPetCondition.setShapeLevel(shapeLevel);
        loadPetCondition.setRankType(rankType);
        loadPetCondition.setPetClass(petClass);
        loadPetCondition.setCurrentPageNum(currentPagerNum);
        loadPetCondition.setPerPageCount(perPagerCount);
        morePetListAsyncTask = (LoadMorePetListAsyncTask) asyncTaskFactory.createAsyncTask(new LoadMorePetListAsyncTask());
        morePetListAsyncTask.execute(loadPetCondition);
    }
    public boolean hasMore(){
        if( currentPetList.size() < perPagerCount){
            return false;
        }else {
            return true;
        }
    }

    public void cancelAsync(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
