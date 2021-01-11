package com.shay.ipets.repository;

import com.google.gson.Gson;
import com.shay.baselibrary.FileTransfromUtil;
import com.shay.baselibrary.MD5CodeCeator;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.NetUtil.RetrofitOnResponseUtil;
import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.ipets.datasource.PostDatasource;
import com.shay.ipets.entity.params.PostParam;
import com.shay.ipets.entity.params.UpLoadPicParam;
import com.shay.ipets.entity.responses.PostResponse;
import java.io.File;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class PostRepository {
    GetResultListener getResultListener;
    GetResultListener uploadPicListener;

    private PostDatasource postDatasource;
    private volatile static PostRepository instance;

    static synchronized public PostRepository getInstance(PostDatasource postDatasource){
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

    public void uploadPic(UpLoadPicParam infoParam, GetResultListener uploadPicListener) throws Exception{
        this.uploadPicListener = uploadPicListener;
        File file = new File(infoParam.getUri());

        infoParam.setToken(UserInfoUtil.getUserToken());

        String json = new Gson().toJson(infoParam);
        byte[] bs = FileTransfromUtil.File2byte(file);
        ResponseBody responseText = ResponseBody.create(MediaType.parse("text/plain"), json);
        ResponseBody responsePic = ResponseBody.create(MediaType.parse("image/*"), bs);
        HashMap<String, ResponseBody> hashMap = new HashMap<>();
        hashMap.put("info", responseText);
        hashMap.put("file", responsePic);
        postDatasource.uploadPic(hashMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<BaseResponse<PostResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<PostResponse> postResponseBaseResponse) {
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
