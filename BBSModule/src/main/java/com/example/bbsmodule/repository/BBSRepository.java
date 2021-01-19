package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.entity.params.GetPostListParam;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.Result;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BBSRepository {
    private BBSDatasource bbsDatasource;
    private volatile static BBSRepository instance;
    private GetResultListener getPostListener;

    private GetResultListener getPostInfoListener;

    public BBSRepository(BBSDatasource bbsDatasource) {
        this.bbsDatasource = bbsDatasource;
    }

    synchronized public static BBSRepository getInstance(BBSDatasource bbsDatasource){
        if(instance == null){
            instance = new BBSRepository(bbsDatasource);
        }
        return instance;
    }

    public void getPostList(GetPostListParam getPostListParam, GetResultListener getPostListener) throws Exception{
        this.getPostListener = getPostListener;
        HashMap<String, Object> map = (HashMap) ObjectTransformUtil.objectToMap(getPostListParam);
        bbsDatasource.getGetList(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse<GetPostListResult>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<GetPostListResult> getPostListResultBaseResponse) {
                Result result = RetrofitOnResponseUtil.parseBaseResponse(getPostListResultBaseResponse);
                setGetPostListResult(result);
            }

            @Override
            public void onError(Throwable e) {

                Result result = RetrofitOnErrorUtil.OnError(e);
                setGetPostListResult(result);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void setGetPostListResult(Result result){
        getPostListener.getResult(result);
    }
    private void setGetPostInfoResult(Result result){
        getPostInfoListener.getResult(result);
    }


    public void setGetPostInfoListener(GetResultListener getPostInfoListener) {
        this.getPostInfoListener = getPostInfoListener;
    }

    public interface GetResultListener{
        void getResult(Result result);
    }

}
