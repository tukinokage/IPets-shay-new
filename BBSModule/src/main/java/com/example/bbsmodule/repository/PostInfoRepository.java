package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.BBSDatasource;
import com.example.bbsmodule.datasource.PostInfoDatasource;
import com.example.bbsmodule.entity.params.GetCommentParam;
import com.example.bbsmodule.entity.params.GetPostInfoParam;
import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.entity.result.GetPostCommentResult;
import com.example.bbsmodule.entity.result.GetPostInfoResult;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostInfoRepository {
    private PostInfoDatasource postInfoDatasource;
    private GetResultListener getPostInfoListener;
    private GetResultListener getCommentListener;
    private volatile static PostInfoRepository instance;

    public PostInfoRepository(PostInfoDatasource postInfoDatasource) {
        this.postInfoDatasource = postInfoDatasource;
    }

    synchronized public static PostInfoRepository getInstance(PostInfoDatasource postInfoDatasource){
        if(instance == null){
            instance = new PostInfoRepository(postInfoDatasource);
        }
        return instance;
    }

    public void getPostInfo(GetPostInfoParam getPostInfoParam, GetResultListener getPostInfoListener) throws Exception{
        this.getPostInfoListener = getPostInfoListener;
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(getPostInfoParam);
        postInfoDatasource.getPostInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GetPostInfoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<GetPostInfoResponse> getPostInfoResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(getPostInfoResponseBaseResponse);
                        getPostInfoListener.getResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        getPostInfoListener.getResult(result);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getPostCommentList(GetCommentParam getCommentParam, GetResultListener getCommentListener) throws Exception{
        this.getCommentListener = getCommentListener;
        HashMap map =(HashMap) ObjectTransformUtil.objectToMap(getCommentParam);
        postInfoDatasource.getPostCommentList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GetCommentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<GetCommentResponse> getCommentResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(getCommentResponseBaseResponse);
                        getCommentListener.getResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        getCommentListener.getResult(result);
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
