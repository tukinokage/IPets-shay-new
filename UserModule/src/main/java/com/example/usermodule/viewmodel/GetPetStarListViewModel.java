package com.example.usermodule.viewmodel;

import android.os.AsyncTask;
import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.UpdateBgParam;
import com.example.usermodule.entity.params.UpdateHeadImgParam;
import com.example.usermodule.entity.params.UpdateUserInfoParam;
import com.example.usermodule.entity.result.UpdateUserInfoResult;
import com.example.usermodule.repository.UpdateUserInfoRepository;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.List;

public class GetPetStarListViewModel extends ViewModel {
    UpdateUserInfoRepository updateUserInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    UpdateInfoAsyncTask updateInfoAsyncTask;
    UpdateBgAsyncTask updateBgAsyncTask;
    UpdateHeadAsyncTask updateHeadAsyncTask;

    private List<Pet> petList;

    private MutableLiveData<List<Pet>> petListMutableLiveData = new MutableLiveData<>();

    public GetPetStarListViewModel(UpdateUserInfoRepository updateUserInfoRepository) {
        this.updateUserInfoRepository = updateUserInfoRepository;
        petList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Pet pet = new Pet();
            pet.setPetName(String.valueOf(i));
            pet.setViewNum(String.valueOf(50));
            petList.add(pet);
        }

    }

    public MutableLiveData<List<Pet>> getPetListMutableLiveData() {
        return petListMutableLiveData;
    }

    class UpdateInfoAsyncTask extends AsyncTask<UpdateUserInfoParam, String, Exception>{

        @Override
        protected Exception doInBackground(UpdateUserInfoParam... updateUserInfoParams) {
            try {
                updateUserInfoRepository.updateUserInfo(updateUserInfoParams[0], new UpdateUserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        UpdateUserInfoResult updateUserInfoResult = new UpdateUserInfoResult();
                        if(result instanceof Result.Error){
                            updateUserInfoResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }
                        updateUserInfoResultMutableLiveData.setValue(updateUserInfoResult);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if(e != null){
                updateUserInfoResultMutableLiveData.setValue(new UpdateUserInfoResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public void getStarPetListData(String id){

    }

    public List<Pet> getCurrentList(){
        return petList;
    }

    public void addPetList(List<Pet> petList){
        petList.addAll(petList);
        petListMutableLiveData.setValue(petList);
    }

    public void removeIndexPet(int position){
        if(petList.size() > position){
            petList.remove(position);
            petListMutableLiveData.setValue(petList);
        }
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
