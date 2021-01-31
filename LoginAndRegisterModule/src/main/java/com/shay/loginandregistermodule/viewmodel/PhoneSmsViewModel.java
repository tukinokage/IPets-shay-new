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

    class SmsAsyncTask extends AsyncTask<AliSmsRequestParam, Integer, Exception>{

        @Override
        protected Exception doInBackground(AliSmsRequestParam... aliSmsRequestParams) {
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

            try {
                phoneSmsRepository.sendMs(map, result -> {

                    //主线程
                    if(result instanceof Result.Success){

                        smsResultLiveData.setValue(new SmsResultStauts(){{setScertCode(codeParam);}});
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
       AliSmsRequestParam aliSmsRequestParam = new AliSmsRequestParam();
       aliSmsRequestParam.setPhoneNumbers(phonenum);
       smsAsyncTask.execute(aliSmsRequestParam);
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
