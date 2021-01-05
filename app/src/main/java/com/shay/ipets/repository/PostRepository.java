package com.shay.ipets.repository;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.ipets.datasource.PostDatasource;
import com.shay.ipets.entity.params.PostParam;
import com.shay.ipets.entity.responses.PostResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostRepository {
    GetResultListener getResultListener;
    GetResultListener uploadPicListener;

    private PostDatasource postDatasource;
    private PostRepository instance;


    synchronized public PostRepository getInstance(PostDatasource postDatasource){
        if (instance == null){
            instance = new PostRepository(postDatasource);
        }
        return instance;
    }

    public PostRepository(PostDatasource postDatasource) {
        this.postDatasource = postDatasource;
    }

    public void post(PostParam postParam, GetResultListener resultListener) throws Exception{
        this.getResultListener = resultListener;
        postParam.setToken(UserInfoUtil.getUserToken());
        postParam.setUserId(UserInfoUtil.getUserId());

        HashMap<String, Object> stringObjectMap = (HashMap<String, Object>) ObjectTransformUtil.objectToMap(postParam);
        postDatasource.postNew(stringObjectMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<PostResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<PostResponse> postResponseBaseResponse) {

                        Result result = RetrofitOnResponseUtil.parseBaseResponse(postResponseBaseResponse);
                        setPosResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setUploadResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void setPosResult(Result result){
        getResultListener.getResult(result);
    }

    public void setUploadResult(Result result){
        uploadPicListener.getResult(result);
    }

    public interface GetResultListener{
        void getResult(Result result);
    }
}
