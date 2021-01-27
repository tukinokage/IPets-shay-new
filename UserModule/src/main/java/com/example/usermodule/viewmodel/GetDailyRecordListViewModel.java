package com.example.usermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserDailyRecordParam;
import com.example.usermodule.entity.params.GetUserPetParam;
import com.example.usermodule.entity.responses.GetDailyRecordResponse;
import com.example.usermodule.entity.responses.GetStarPetListResponse;
import com.example.usermodule.entity.result.GetDaliyRecordResult;
import com.example.usermodule.entity.result.GetStarPetListResult;
import com.example.usermodule.repository.UserInfoRepository;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.UserDailyRecordItem;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.List;

public class GetDailyRecordListViewModel extends ViewModel {
    UserInfoRepository userInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    GetDailyRecordAsyncTask getDailyRecordAsyncTask;

    private List<UserDailyRecordItem> dailyRecordItemList;

    private MutableLiveData<List<UserDailyRecordItem>>  recordListMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<GetDaliyRecordResult>  recordResultMutableLiveData = new MutableLiveData<>();

    public GetDailyRecordListViewModel(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        dailyRecordItemList = new ArrayList<>();
    }

    public MutableLiveData<List<UserDailyRecordItem>> getRecordListMutableLiveData() {
        return recordListMutableLiveData;
    }

    public MutableLiveData<GetDaliyRecordResult> getRecordResultMutableLiveData() {
        return recordResultMutableLiveData;
    }

    class GetDailyRecordAsyncTask  extends AsyncTask<GetUserDailyRecordParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetUserDailyRecordParam... getUserDailyRecordParams) {
            try {
                userInfoRepository.getDailyRecordList(getUserDailyRecordParams[0], new UserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetDaliyRecordResult getDaliyRecordResult = new GetDaliyRecordResult();
                        if(result instanceof Result.Error){
                            getDaliyRecordResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else{
                            GetDailyRecordResponse getDailyRecordResponse = (GetDailyRecordResponse) ((Result.Success)result).getData();
                            getDaliyRecordResult.setDailyRecordItemList(getDailyRecordResponse.getDailyRecordItemList());
                        }
                        recordResultMutableLiveData .setValue(getDaliyRecordResult);
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
                recordResultMutableLiveData.setValue(new GetDaliyRecordResult() {{setErrorMsg("应用出错");}});
            }
        }
    }

    public void getDailyRecordData(String id, int perPagerCount, int currentPager){
        GetUserDailyRecordParam param = new GetUserDailyRecordParam();
        param.setCurrentPager(String.valueOf(currentPager));
        param.setPerPagerCount(String.valueOf(perPagerCount));
        param.setUserId(id);
        getDailyRecordAsyncTask = (GetDailyRecordAsyncTask) asyncTaskFactory.createAsyncTask(new GetDailyRecordAsyncTask());
        getDailyRecordAsyncTask.execute(param);
    }

    public List<UserDailyRecordItem> getDailyRecordItemList() {
        return dailyRecordItemList;
    }

    public void addDailyRecordList(List list){
        dailyRecordItemList.addAll(list);
        recordListMutableLiveData.setValue(list);
    }


    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
