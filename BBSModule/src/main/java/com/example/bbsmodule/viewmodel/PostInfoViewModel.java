package com.example.bbsmodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.adapter.PostInfoListAdapter;
import com.example.bbsmodule.entity.params.GetCommentParam;
import com.example.bbsmodule.entity.params.GetPostInfoParam;
import com.example.bbsmodule.entity.response.GetCommentResponse;
import com.example.bbsmodule.entity.response.GetPostInfoResponse;
import com.example.bbsmodule.entity.response.GetPostListResponse;
import com.example.bbsmodule.entity.result.GetPostCommentResult;
import com.example.bbsmodule.entity.result.GetPostInfoResult;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.repository.BBSRepository;
import com.example.bbsmodule.repository.PostInfoRepository;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.List;

public class PostInfoViewModel extends ViewModel {
    private PostInfoRepository postInfoRepository;

    private MutableLiveData<GetPostInfoResult> getPostInfoResultMutableLiveData;
    private MutableLiveData<GetPostCommentResult> getPostCommentResultMutableLiveData;

    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    GetPostCommentAsyncTask getPostCommentAsyncTask;
    GetPostInfoAsyncTask getPostInfoAsyncTask;

    public PostInfoViewModel(PostInfoRepository postInfoRepository) {
        this.postInfoRepository = postInfoRepository;
    }

    public MutableLiveData<GetPostInfoResult> getGetPostInfoResultMutableLiveData() {
        return getPostInfoResultMutableLiveData;
    }

    public MutableLiveData<GetPostCommentResult> getGetPostCommentResultMutableLiveData() {
        return getPostCommentResultMutableLiveData;
    }
    class GetPostInfoAsyncTask extends AsyncTask<GetPostInfoParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetPostInfoParam... getPostInfoParams) {

            try {
                GetPostInfoParam getPostInfoParam = getPostInfoParams[0];
                postInfoRepository.getPostInfo(getPostInfoParam, new PostInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetPostInfoResult getPostInfoResult = new GetPostInfoResult();
                        if(result instanceof Result.Success){
                            GetPostInfoResponse response = (GetPostInfoResponse) ((Result.Success) result).getData();
                            getPostInfoResult.setPost(response.getPost());
                        }else {
                            String error = ((Result.Error) result).getErrorMsg();
                            getPostInfoResult.setErrorMsg(error);
                        }

                        getGetPostInfoResultMutableLiveData().setValue(getPostInfoResult);
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
                getPostInfoResultMutableLiveData.setValue(new GetPostInfoResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    class GetPostCommentAsyncTask extends AsyncTask<GetCommentParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetCommentParam... getCommentParams) {

            try {
                GetCommentParam getCommentParam = getCommentParams[0];
                postInfoRepository.getPostCommentList(getCommentParam, new PostInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetPostCommentResult getPostCommentResult = new GetPostCommentResult();
                        if(result instanceof Result.Success){
                            GetCommentResponse response = (GetCommentResponse) ((Result.Success) result).getData();
                            getPostCommentResult.setCommentList(response.getCommentsList());
                        }else {
                            String error = ((Result.Error) result).getErrorMsg();
                            getPostCommentResult.setErrorMsg(error);
                        }

                        getGetPostCommentResultMutableLiveData().setValue(getPostCommentResult);
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
                getPostInfoResultMutableLiveData.setValue(new GetPostInfoResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    public void getPost(String id){
        GetPostInfoParam getPostInfoParam = new GetPostInfoParam();
        getPostInfoParam.setPostId(id);
        getPostInfoAsyncTask = (GetPostInfoAsyncTask) asyncTaskFactory.
                createAsyncTask(new GetPostInfoAsyncTask());
        getPostInfoAsyncTask.execute(getPostInfoParam);
    }

    public void getComment(String id){
        GetCommentParam commentParam = new GetCommentParam();
        commentParam.setPostId(id);
        getPostCommentAsyncTask = (GetPostCommentAsyncTask) asyncTaskFactory.
                createAsyncTask(new GetPostCommentAsyncTask());
        getPostCommentAsyncTask.execute(commentParam);
    }

    public void sendComment(String postId, String userId, String contentText, List<String> succeedPicList){

    }

    public void cancelAsyTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
