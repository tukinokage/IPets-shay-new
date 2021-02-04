package com.shay.loginandregistermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.apiinfoparamsUtils.AliApiParams;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.entity.params.AliSmsRequestParam;
import com.shay.loginandregistermodule.data.entity.params.ConfrimPhoneNumParam;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.loginandregistermodule.data.entity.params.SendMsgParam;
import com.shay.loginandregistermodule.data.entity.responsedata.PhoneReponseData;
import com.shay.loginandregistermodule.data.entity.responsedata.SmsResponse;
import com.shay.loginandregistermodule.data.entity.result.ComfrimPhoneResult;
import com.shay.loginandregistermodule.data.entity.result.SmsResultStauts;
import com.shay.loginandregistermodule.data.repository.PhoneSmsRepository;

import java.util.HashMap;
import java.util.Random;

public class PhoneSmsViewModel extends ViewModel {

    private MutableLiveData<SmsResultStauts> smsResultLiveData = new MutableLiveData<>();
    private MutableLiveData<ConfrimPhoneResult> confrimPhoneResultMutableLiveData = new MutableLiveData<>();
    private PhoneSmsRepository phoneSmsRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private ConfrimAsyncTask confrimAsyncTask;
    private SmsAsyncTask smsAsyncTask;
    private String codeParam = "";

    public PhoneSmsViewModel(PhoneSmsRepository phoneSmsRepository) {
        this.phoneSmsRepository = phoneSmsRepository;
    }

    public MutableLiveData<ConfrimPhoneResult> getConfrimPhoneResultMutableLiveData() {
        return confrimPhoneResultMutableLiveData;
    }

    class SmsAsyncTask extends AsyncTask<SendMsgParam, Integer, Exception>{

        @Override
        protected Exception doInBackground(SendMsgParam... sendMsgParams) {
            SendMsgParam param = sendMsgParams[0];

            String gsonParam = new Gson().toJson(param);
            HashMap<String, Object> map = new Gson().fromJson(gsonParam, HashMap.class);

            try {
                phoneSmsRepository.sendMs(map, result -> {

                    //主线程
                    if(result instanceof Result.Success){
                        SmsResultStauts smsResultStauts = new SmsResultStauts();
                        smsResultLiveData.setValue(smsResultStauts);
                    }else{
                        String msg = ((Result.Error)result).getErrorMsg();
                        smsResultLiveData.setValue(new SmsResultStauts(){{setErrorMsg(msg);}});
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
                e.printStackTrace();
                smsResultLiveData.setValue(new SmsResultStauts(){{setErrorMsg("应用错误");}});
            }
        }
    }


    class ConfrimAsyncTask extends AsyncTask<ConfrimPhoneNumParam, String, Exception>{


        @Override
        protected Exception doInBackground(ConfrimPhoneNumParam... confrimPhoneNumParams) {
            try {
                ConfrimPhoneNumParam confrimPhoneNumParam = confrimPhoneNumParams[0];
                Gson gson = new Gson();
                String json = gson.toJson(confrimPhoneNumParam);
                HashMap<String, Object> paramHashMap = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
                phoneSmsRepository.getPhoneToken(paramHashMap, new PhoneSmsRepository.SmsResultListener() {
                    @Override
                    public void getSmsResult(Result result) {
                        ConfrimPhoneResult phoneResult = new ConfrimPhoneResult();
                        if(result instanceof Result.Error){
                            phoneResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else if(result instanceof Result.Success){
                            PhoneReponseData phoneReponseData = (PhoneReponseData) ((Result.Success) result).getData();
                            phoneResult.setPhoneToken(phoneReponseData.getPhoneToken());
                        }

                        confrimPhoneResultMutableLiveData.setValue(phoneResult);
                    }
                });
                /**
                 *
                 * **/

                return null;
            }catch (Exception e){

                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);

            if(e != null){
                e.printStackTrace();
                confrimPhoneResultMutableLiveData.setValue(new ConfrimPhoneResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public void sendSms(String phonenum){
       smsAsyncTask = (SmsAsyncTask) asyncTaskFactory.createAsyncTask(new SmsAsyncTask());
        SendMsgParam param = new SendMsgParam();
        param.setPhoneNum(phonenum);
       smsAsyncTask.execute(param);
    }

    public void confrimPhone(String phoneNum, String code){
        confrimAsyncTask = (ConfrimAsyncTask) asyncTaskFactory.createAsyncTask(new ConfrimAsyncTask());
        confrimAsyncTask.execute(new ConfrimPhoneNumParam(phoneNum, code));

    }


    public LiveData<SmsResultStauts> getSmsResultLiveData() {
        return smsResultLiveData;
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
