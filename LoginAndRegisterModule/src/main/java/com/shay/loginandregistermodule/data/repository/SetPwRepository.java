package com.shay.loginandregistermodule.data.repository;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.loginandregistermodule.data.datasource.SetPwDataSource;
import com.shay.loginandregistermodule.data.entity.responsedata.SetPwResponseData;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SetPwRepository {

    private SetPwDataSource setPwDataSource;
    private volatile SetPwRepository instance;
    private SetPwResultListener setPwResultListener;

    public SetPwRepository(SetPwDataSource setPwDataSource) {
        this.setPwDataSource = setPwDataSource;
    }


    synchronized public SetPwRepository getInstance(){
        if(instance == null){
            instance = new SetPwRepository(setPwDataSource);
        }
        return instance;
    }

    public void setPassword(HashMap<String, Object> paramsMap, SetPwResultListener setPwResultListener) throws Exception{
        this.setPwResultListener = setPwResultListener;
        setPwDataSource.setPw(paramsMap)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SetPwResponseData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<SetPwResponseData> setPwResponseDataBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(setPwResponseDataBaseResponse);
                        setPwResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                       Result result = RetrofitOnErrorUtil.OnError(e);
                       setPwResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setPwResult(Result result){
        setPwResultListener.getResult(result);
    }

    public interface SetPwResultListener{
        void getResult(Result result);
    }
}
