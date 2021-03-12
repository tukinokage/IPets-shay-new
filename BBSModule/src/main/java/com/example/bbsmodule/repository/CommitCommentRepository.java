package com.example.bbsmodule.repository;

import com.example.bbsmodule.datasource.CommitCommentDataSource;
import com.example.bbsmodule.datasource.PostInfoDatasource;
import com.example.bbsmodule.entity.params.CommitCommentParam;
import com.example.bbsmodule.entity.response.CommitCommentResponse;
import com.google.gson.Gson;
import com.shay.baselibrary.FileTransfromUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.params.UpLoadPicParam;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CommitCommentRepository {
    private CommitCommentDataSource commitCommentDataSource;
    private GetResultListener uploadPicListener;
    private GetResultListener commitCommentListener;
    private volatile static CommitCommentRepository instance;

    public CommitCommentRepository(CommitCommentDataSource commitCommentDataSource) {
        this.commitCommentDataSource = commitCommentDataSource;
    }

    synchronized public static CommitCommentRepository getInstance(CommitCommentDataSource commitCommentDataSource){
        if(instance == null){
            instance = new CommitCommentRepository(commitCommentDataSource);
        }
        return instance;
    }

    public void commitComment(CommitCommentParam commitCommentParam, GetResultListener commitCommentListener) throws Exception{
        this.commitCommentListener = commitCommentListener;
        commitCommentParam.setUserId(UserInfoUtil.getUserId());
        commitCommentParam.setToken(UserInfoUtil.getUserToken());
        HashMap map = (HashMap) ObjectTransformUtil.objectToMap(commitCommentParam);
        commitCommentDataSource.commentNew(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<CommitCommentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CommitCommentResponse> o) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(o);
                        setCommitCommentResult(result);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Result result = RetrofitOnErrorUtil.OnError(e);
                        setCommitCommentResult(result);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void uploadPic(UpLoadPicParam infoParam, GetResultListener uploadPicListener) throws Exception{
        this.uploadPicListener = uploadPicListener;
        File file = new File(infoParam.getUri());

        infoParam.setToken(UserInfoUtil.getUserToken());
        infoParam.setUserId(UserInfoUtil.getUserId());

        String json = new Gson().toJson(infoParam);
        RequestBody responseText = RequestBody.create(MediaType.parse("text/plain"), json);
        RequestBody responsePic = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), responsePic);
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);
        commitCommentDataSource.uploadPic(hashMap, part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UpLoadPicResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UpLoadPicResponse> postResponseBaseResponse) {
                        Result result = RetrofitOnResponseUtil.parseBaseResponse(postResponseBaseResponse);
                        setUploadResult(result);
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


    public void setUploadResult(Result result){
        uploadPicListener.getResult(result);
    }
    public void setCommitCommentResult(Result result){
        commitCommentListener.getResult(result);
    }

    public interface GetResultListener{
        void getResult(Result result);
    }

}
