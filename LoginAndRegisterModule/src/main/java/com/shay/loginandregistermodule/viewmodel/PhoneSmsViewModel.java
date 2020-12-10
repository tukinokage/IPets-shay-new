package com.shay.loginandregistermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.shay.baselibrary.apiinfoparamsUtils.AliApiParams;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.model.AliSmsRequestParam;
import com.shay.loginandregistermodule.data.model.SmsResultStauts;
import com.shay.loginandregistermodule.data.repository.PhoneSmsRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PhoneSmsViewModel extends ViewModel {

    private MutableLiveData<SmsResultStauts> smsResultLiveData = new MutableLiveData<>();

    private PhoneSmsRepository phoneSmsRepository;

    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private SmsAsyncTask smsAsyncTask;
    private String codeParam = "";


    public PhoneSmsViewModel(PhoneSmsRepository phoneSmsRepository) {
        this.phoneSmsRepository = phoneSmsRepository;
    }

    class SmsAsyncTask extends AsyncTask<AliSmsRequestParam, Integer, Integer>{

        @Override
        protected Integer doInBackground(AliSmsRequestParam... aliSmsRequestParams) {
            AliSmsRequestParam param = aliSmsRequestParams[0];

            Random random = new Random();
            int codeInt = random.nextInt(8999) + 1000;

            //request params
            codeParam = String.valueOf(codeInt);
            param.setTemplateParam(codeParam);
            param.setTemplateCode(AliApiParams.SmsApi.TEMPLATE_CODE_LOGINRESGISTER);
            param.setSignName(AliApiParams.SmsApi.SIGN_NAME);
            String gsonParam = new Gson().toJson(param);
            HashMap<String, Object> map = new Gson().fromJson(gsonParam, HashMap.class);

            phoneSmsRepository.sendMs(map, result -> {

                //主线程
                if(result instanceof Result.Success){

                    smsResultLiveData.setValue(new SmsResultStauts(){{setScertCode(codeParam);}});
                }else{

                    String msg = ((Result.Error)result).getErrorMsg();
                    smsResultLiveData.setValue(new SmsResultStauts(){{setErrorMsg(msg);}});
                }

            });
            return null;
        }
    }
    public void sendSms(String phonenum){
       smsAsyncTask = (SmsAsyncTask) asyncTaskFactory.createAsyncTask(new SmsAsyncTask());
       AliSmsRequestParam aliSmsRequestParam = new AliSmsRequestParam();
       aliSmsRequestParam.setPhoneNumbers(phonenum);
       smsAsyncTask.execute(aliSmsRequestParam);
    }


    public LiveData<SmsResultStauts> getSmsResultLiveData() {
        return smsResultLiveData;
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }


}
