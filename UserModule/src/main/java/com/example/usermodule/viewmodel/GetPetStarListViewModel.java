package com.example.usermodule.viewmodel;

import android.os.AsyncTask;
import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserCommentParam;
import com.example.usermodule.entity.params.GetUserPetParam;
import com.example.usermodule.entity.params.UpdateBgParam;
import com.example.usermodule.entity.params.UpdateHeadImgParam;
import com.example.usermodule.entity.params.UpdateUserInfoParam;
import com.example.usermodule.entity.responses.GetStarPetListResponse;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.result.GetStarPetListResult;
import com.example.usermodule.entity.result.UpdateUserInfoResult;
import com.example.usermodule.repository.UpdateUserInfoRepository;
import com.example.usermodule.repository.UserInfoRepository;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.List;

public class GetPetStarListViewModel extends ViewModel {
    UserInfoRepository userInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    GetStarPetListAsyncTask getStarPetListAsyncTask;

    private List<Pet> petList;

    private MutableLiveData<List<Pet>> petListMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<GetStarPetListResult>  starPetListResultMutableLiveData = new MutableLiveData<>();

    public GetPetStarListViewModel(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        petList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Pet pet = new Pet();
            pet.setPetName(String.valueOf(i));
            pet.setViewNum(String.valueOf(50));
            petList.add(pet);
        }

    }

    public MutableLiveData<GetStarPetListResult> getStarPetListResultMutableLiveData() {
        return starPetListResultMutableLiveData;
    }
    public MutableLiveData<List<Pet>> getPetListMutableLiveData() {
        return petListMutableLiveData;
    }

    class GetStarPetListAsyncTask  extends AsyncTask<GetUserPetParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetUserPetParam... getUserPetParams) {
            try {
                userInfoRepository.getStarPetList(getUserPetParams[0], new UserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetStarPetListResult getStarPetListResult = new GetStarPetListResult();
                        if(result instanceof Result.Error){
                            getStarPetListResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else{
                            GetStarPetListResponse getStarPetListResponse = (GetStarPetListResponse) ((Result.Success)result).getData();
                            getStarPetListResult.setPetList(getStarPetListResponse.getPetList());
                        }
                        starPetListResultMutableLiveData.setValue(getStarPetListResult);
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
                starPetListResultMutableLiveData.setValue(new GetStarPetListResult() {{setErrorMsg("应用出错");}});
            }
        }
    }

    class RemoveStarPetAsyncTask extends AsyncTask<GetUserPetParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetUserPetParam... getUserPetParams) {
            try {
                userInfoRepository.getStarPetList(getUserPetParams[0], new UserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetStarPetListResult getStarPetListResult = new GetStarPetListResult();
                        if(result instanceof Result.Error){
                            getStarPetListResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else{
                            GetStarPetListResponse getStarPetListResponse = (GetStarPetListResponse) ((Result.Success)result).getData();
                            getStarPetListResult.setPetList(getStarPetListResponse.getPetList());
                        }
                        starPetListResultMutableLiveData.setValue(getStarPetListResult);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if(e != null){
                starPetListResultMutableLiveData.setValue(new GetStarPetListResult() {{setErrorMsg("应用出错");}});
            }
        }
    }

    public void getStarPetListData(String id, int perPagerCount, int currentPager){
        GetUserPetParam param = new GetUserPetParam();
        param.setCurrentPager(String.valueOf(currentPager));
        param.setPerPagerCount(String.valueOf(perPagerCount));
        param.setUserId(id);
        getStarPetListAsyncTask = (GetStarPetListAsyncTask) asyncTaskFactory.createAsyncTask(new GetStarPetListAsyncTask());
        getStarPetListAsyncTask.execute(param);
    }

    public List<Pet> getCurrentList(){
        return petList;
    }

    public void addPetList(List<Pet> petList){
        petList.addAll(petList);
        petListMutableLiveData.setValue(petList);
    }

    public void removeIndexPet(int position, String userId){
        if(petList.size() > position){
            petList.remove(position);
            petListMutableLiveData.setValue(petList);
        }
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
