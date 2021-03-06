package com.shay.ipets.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.ipets.entity.params.ConfrimDaliyRecord;
import com.shay.ipets.entity.result.ConfirmRecordResult;
import com.shay.ipets.repository.DaliyRecordRepository;
import java.util.HashMap;

public class DaliRecordModel extends ViewModel {
    private DaliyRecordRepository daliyRecordRepository;
    private MutableLiveData<ConfirmRecordResult> confirmRecordResult = new MutableLiveData<>();
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private ConfirmDailyRecordAsyncTask confirmDailyRecordAsyncTask;
    public DaliRecordModel(DaliyRecordRepository daliyRecordRepository) {
        this.daliyRecordRepository = daliyRecordRepository;
    }
    public MutableLiveData<ConfirmRecordResult> getConfirmRecordResult() {
        return confirmRecordResult;
    }
    class ConfirmDailyRecordAsyncTask extends AsyncTask<ConfrimDaliyRecord, String, Exception> {

        @Override
        protected Exception doInBackground(ConfrimDaliyRecord... confrimDaliyRecords) {
            try{
                ConfrimDaliyRecord confrimDaliyRecord = confrimDaliyRecords[0];
                daliyRecordRepository.postDailyRecord(confrimDaliyRecord, new DaliyRecordRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        ConfirmRecordResult confirmResult = new ConfirmRecordResult();
                        if(result instanceof Result.Success){
                            /*
                            *
                            * */
                        }else {
                            String errorMsg = ((Result.Error)result).getErrorMsg();
                            confirmResult.setErrorMsg(errorMsg);
                        }
                        confirmRecordResult.setValue(confirmResult);
                    }
                });
            }catch (Exception e) {
                return e;
            }

            return null;

        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if(e != null){
                e.printStackTrace();

            }
        }
    }

    public void confirmDailyRecord(String contentText){
        confirmDailyRecordAsyncTask = (ConfirmDailyRecordAsyncTask) asyncTaskFactory
                .createAsyncTask(new ConfirmDailyRecordAsyncTask());

        ConfrimDaliyRecord confrimDaliyRecord = new ConfrimDaliyRecord();
        confrimDaliyRecord.setContent(contentText);
        confirmDailyRecordAsyncTask.execute(confrimDaliyRecord);

    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
