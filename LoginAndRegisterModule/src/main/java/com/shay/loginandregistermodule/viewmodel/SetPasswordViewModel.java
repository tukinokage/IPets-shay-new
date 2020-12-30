package com.shay.loginandregistermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.entity.params.SetPwRequestParam;
import com.shay.loginandregistermodule.data.entity.result.SetPwResult;
import com.shay.loginandregistermodule.data.repository.SetPwRepository;

import java.util.HashMap;
import java.util.Set;

public class SetPasswordViewModel extends ViewModel {
    private MutableLiveData<SetPwResult> setPwResultMutableLiveData = new MutableLiveData<>();
    private SetPwRepository setPwRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();

    public SetPasswordViewModel(SetPwRepository setPwRepository) {
        this.setPwRepository = setPwRepository;
    }

    public MutableLiveData<SetPwResult> getSetPwResultMutableLiveData() {
        return setPwResultMutableLiveData;
    }

    class SetPwAsyncTask extends AsyncTask<SetPwRequestParam, String, Exception> {

        @Override
        protected Exception doInBackground(SetPwRequestParam... setPwRequestParams) {
            SetPwRequestParam setPwRequestParam = setPwRequestParams[0];
            Gson gson = new Gson();
            String json = gson.toJson(setPwRequestParam);
            HashMap<String, Object> param = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
            try {
                setPwRepository.setPassword(param, new SetPwRepository.SetPwResultListener() {
                    //rxjava回调在主线程
                    @Override
                    public void getResult(Result result) {
                        if(result instanceof Result.Error){
                            setPwResultMutableLiveData.setValue(new SetPwResult(){{setErrorMsg(((Result.Error) result).getErrorMsg());}});
                        }else {
                            setPwResultMutableLiveData.setValue(new SetPwResult(){{setErrorMsg("");}});
                        }
                    }
                });

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if(e != null){
                getSetPwResultMutableLiveData().setValue(new SetPwResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public void confirm(String pw)  {
        AsyncTask asyncTask = asyncTaskFactory.createAsyncTask(new SetPwAsyncTask());
        SetPwRequestParam setPwRequestParam = null;
        try {
            setPwRequestParam = new SetPwRequestParam(UserInfoUtil.getUserId(), pw);
            asyncTask.execute(setPwRequestParam);
        } catch (Exception e) {
            e.printStackTrace();
            setPwResultMutableLiveData.setValue(new SetPwResult(){{setErrorMsg("无法获取用户信息");}});
        }
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
