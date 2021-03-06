package com.example.usermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.repository.UserInfoRepository;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.baselibrary.myexceptions.MyException;

public class UserInfoViewModel extends ViewModel {
    UserInfoRepository userInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();

    GetUserInfoAsyncTask getUserInfoAsyncTask;
    class GetUserInfoAsyncTask extends AsyncTask<GetUserInfoParam , String, Exception>{

        @Override
        protected Exception doInBackground(GetUserInfoParam... getUserInfoParams) {
            try {
                userInfoRepository.getUserInfo(getUserInfoParams[0], new UserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetUserResult getUserResult = new GetUserResult();
                        if(result instanceof Result.Error){
                            getUserResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else {
                            GetUserInfoResponse getUserInfoResponse = (GetUserInfoResponse) ((Result.Success)result).getData();
                            getUserResult.setUserInfo(getUserInfoResponse.getUserInfo());
                        }
                        getUserResultMutableLiveData.setValue(getUserResult);
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
                getUserResultMutableLiveData.setValue(new GetUserResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public void setGetUserResultMutableLiveData(MutableLiveData<GetUserResult> getUserResultMutableLiveData) {
        this.getUserResultMutableLiveData = getUserResultMutableLiveData;
    }

    private MutableLiveData<GetUserResult> getUserResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GetUserResult> getGetUserResultMutableLiveData() {
        return getUserResultMutableLiveData;
    }
    public UserInfoViewModel(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public void getUserInfo(String id){
        GetUserInfoParam getUserInfoParam = new GetUserInfoParam();
        getUserInfoParam.setUserId(id);
        getUserInfoAsyncTask = (GetUserInfoAsyncTask) asyncTaskFactory.createAsyncTask(new GetUserInfoAsyncTask());
        getUserInfoAsyncTask.execute(getUserInfoParam);
    }

     public void getMyInfo(String id){
         GetUserInfoParam getUserInfoParam = new GetUserInfoParam();
         getUserInfoParam.setUserId(id);
         getUserInfoAsyncTask = (GetUserInfoAsyncTask) asyncTaskFactory.createAsyncTask(new GetUserInfoAsyncTask());
         getUserInfoAsyncTask.execute(getUserInfoParam);
     }


     public String getMyId() throws Exception {
         if(UserInfoUtil.getUserId() == null){
             throw new MyException("身份失效");
         }
         return UserInfoUtil.getUserId();
     }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
