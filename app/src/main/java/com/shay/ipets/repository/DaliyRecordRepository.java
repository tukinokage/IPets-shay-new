package com.shay.ipets.repository;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.ipets.datasource.DailyRecordDatasource;
import com.shay.ipets.entity.params.ConfrimDaliyRecord;
import com.shay.ipets.entity.responses.PostDaliyResponse;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DaliyRecordRepository {
    private DailyRecordDatasource dailyRecordDatasource;
    private volatile static DaliyRecordRepository instance;
    private GetResultListener postResultListener;

    public DaliyRecordRepository(DailyRecordDatasource dailyRecordDatasource) {
        this.dailyRecordDatasource = dailyRecordDatasource;
    }

    synchronized public static DaliyRecordRepository getInstance(DailyRecordDatasource dailyRecordDatasource){
        if(instance == null){
            instance = new DaliyRecordRepository(dailyRecordDatasource);
        }
        return instance;
    }

    public void postDailyRecord(ConfrimDaliyRecord record, GetResultListener resultListener) throws Exception{
        this.postResultListener = resultListener;
        record.setUserId(UserInfoUtil.getUserId());
        record.setToken(UserInfoUtil.getUserToken());

        HashMap hashMap = (HashMap) ObjectTransformUtil.objectToMap(record);
        dailyRecordDatasource.postDaily(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<PostDaliyResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<PostDaliyResponse> postDaliyResponseBaseResponse) {
                       Result result = RetrofitOnResponseUtil.parseBaseResponse(postDaliyResponseBaseResponse);
                        postResultListener.getResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        postResultListener.getResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface GetResultListener{
        void getResult(Result result);
    }
}
