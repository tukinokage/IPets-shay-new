package com.example.usermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.repository.UpdateUserInfoRepository;
import com.example.usermodule.repository.UserInfoRepository;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.baselibrary.myexceptions.MyException;

public class UpdateInfoViewModel extends ViewModel {
    UpdateUserInfoRepository updateUserInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    GetUserInfoAsyncTask getUserInfoAsyncTask;
    class GetUserInfoAsyncTask extends AsyncTask<GetUserInfoParam , String, Exception>{

        @Override
        protected Exception doInBackground(GetUserInfoParam... getUserInfoParams) {
            try {
                updateUserInfoRepository.getUserInfo(getUserInfoParams[0], new UserInfoRepository.GetResultListener() {
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



    private MutableLiveData<GetUserResult> getUserResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GetUserResult> getGetUserResultMutableLiveData() {
        return getUserResultMutableLiveData;
    }
    public UpdateInfoViewModel(UpdateUserInfoRepository updateUserInfoRepository) {
        this.updateUserInfoRepository = updateUserInfoRepository;
    }

    public void uploadHeadImg(String uri){

    }

    public void uploadBg(String uri){

    }


    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
