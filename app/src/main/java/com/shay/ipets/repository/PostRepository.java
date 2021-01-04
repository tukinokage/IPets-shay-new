package com.shay.ipets.repository;

import com.shay.baselibrary.ObjectTransformUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.ipets.datasource.PostDatasource;
import com.shay.ipets.entity.params.PostParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {
    GetResultListener getResultListener;

    private PostDatasource postDatasource;
    private PostRepository instance;

    private List<String> selectedPicList;
    private List<String> succeedPicList;

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

    }

    public interface GetResultListener{
        void getResult(Result result);
    }
}
