package com.shay.ipets.viewmodel;

import android.os.AsyncTask;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shay.baselibrary.dto.Picture;
import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.ipets.entity.params.PostParam;
import com.shay.ipets.entity.result.PostResult;
import com.shay.ipets.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {

    private List<Picture> succeedPicList = new ArrayList<>();
    private List<PostPicInfo> selectPicList = new ArrayList<>();

    private PostRepository postRepository;
    private MutableLiveData<PostResult> postResultMutableLiveData = new MutableLiveData<>();

    private AsyncTaskFactory asyncTaskFactory;
    private PostAsyncTask postAsyncTask;
    class PostAsyncTask extends AsyncTask<PostParam, String, Exception>{

        @Override
        protected Exception doInBackground(PostParam... postParams) {
            PostParam postParam = postParams[0];
            try {
                postRepository.post(postParam, new PostRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        PostResult postResult = new PostResult();
                        if(result instanceof Result.Success){

                        }else {
                            String error = ((Result.Error) result).getErrorMsg();
                            postResult.setErrorMsg(error);
                        }
                        postResultMutableLiveData.setValue(postResult);
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
                postResultMutableLiveData.setValue(new PostResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public PostViewModel(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void post(String title, String ContentText, int type, List<Picture> picList){
        postAsyncTask = (PostAsyncTask) asyncTaskFactory.createAsyncTask(new PostAsyncTask());
        PostParam postParam = new PostParam();
        postParam.setTitle(title);
        postParam.setContentText(ContentText);
        postParam.setPicList(picList);
        postParam.setType(type);
        postAsyncTask.execute(postParam);
    }

    public void uploadPic(Picture picture){

    }

    public void selectPic(){

    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
