package com.example.usermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserInfoParam;
import com.example.usermodule.entity.params.UpdateBgParam;
import com.example.usermodule.entity.params.UpdateHeadImgParam;
import com.example.usermodule.entity.params.UpdateUserInfoParam;
import com.example.usermodule.entity.responses.GetUserInfoResponse;
import com.example.usermodule.entity.responses.UpdateUserInfoResponse;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.entity.result.UpdateUserInfoResult;
import com.example.usermodule.repository.UpdateUserInfoRepository;
import com.example.usermodule.repository.UserInfoRepository;
import com.example.usermodule.services.UpdateInfoService;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.baselibrary.myexceptions.MyException;
import com.wildma.pictureselector.PictureSelector;

public class UpdateInfoViewModel extends ViewModel {
    UpdateUserInfoRepository updateUserInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    UpdateInfoAsyncTask updateInfoAsyncTask;
    UpdateBgAsyncTask updateBgAsyncTask;
    UpdateHeadAsyncTask updateHeadAsyncTask;

    private MutableLiveData<UpdateUserInfoResult> updateUserInfoResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UpdateUserInfoResult> updateHeadResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UpdateUserInfoResult> updateBgResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<UpdateUserInfoResult> getUpdateUserInfoResultMutableLiveData() {
        return updateUserInfoResultMutableLiveData;
    }

    public MutableLiveData<UpdateUserInfoResult> getUpdateHeadResultMutableLiveData() {
        return updateHeadResultMutableLiveData;
    }

    public MutableLiveData<UpdateUserInfoResult> getUpdateBgResultMutableLiveData() {
        return updateBgResultMutableLiveData;
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

    class UpdateHeadAsyncTask extends AsyncTask<UpdateHeadImgParam, String, Exception>{

        @Override
        protected Exception doInBackground(UpdateHeadImgParam... updateUserInfoParams) {
            try {
                updateUserInfoRepository.updateHeadImg(updateUserInfoParams[0], new UpdateUserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        UpdateUserInfoResult updateUserInfoResult = new UpdateUserInfoResult();
                        if(result instanceof Result.Error){
                            updateUserInfoResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }
                        updateHeadResultMutableLiveData.setValue(updateUserInfoResult);
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
                updateHeadResultMutableLiveData.setValue(new UpdateUserInfoResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    class UpdateBgAsyncTask extends AsyncTask<UpdateBgParam, String, Exception>{

        @Override
        protected Exception doInBackground(UpdateBgParam... updateBgParams) {
            try {
                updateUserInfoRepository.updateBg(updateBgParams[0], new UpdateUserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        UpdateUserInfoResult updateUserInfoResult = new UpdateUserInfoResult();
                        if(result instanceof Result.Error){
                            updateUserInfoResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }
                        updateBgResultMutableLiveData.setValue(updateUserInfoResult);
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
                updateBgResultMutableLiveData.setValue(new UpdateUserInfoResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public UpdateInfoViewModel(UpdateUserInfoRepository updateUserInfoRepository) {
        this.updateUserInfoRepository = updateUserInfoRepository;
    }

    public void uploadHeadImg(String uri){
        UpdateHeadImgParam param = new UpdateHeadImgParam();
        param.setUri(uri);
        updateHeadAsyncTask= (UpdateHeadAsyncTask) asyncTaskFactory.createAsyncTask(new UpdateHeadAsyncTask());
        updateHeadAsyncTask.execute(param);
    }

    public void uploadBg(String uri){
        UpdateBgParam param = new UpdateBgParam();
        param.setUri(uri);
        updateBgAsyncTask = (UpdateBgAsyncTask) asyncTaskFactory.createAsyncTask(new UpdateBgAsyncTask());
        updateBgAsyncTask.execute(param);
    }

    public void updateInfo(String name, String sign){
        UpdateUserInfoParam param = new UpdateUserInfoParam();
        param.setSign(sign);
        param.setUserName(name);
        updateInfoAsyncTask = (UpdateInfoAsyncTask) asyncTaskFactory.createAsyncTask(new UpdateBgAsyncTask());
        updateInfoAsyncTask.execute(param);
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
