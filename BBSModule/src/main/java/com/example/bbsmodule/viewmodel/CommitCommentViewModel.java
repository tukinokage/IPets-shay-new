package com.example.bbsmodule.viewmodel;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.entity.params.CommitCommentParam;
import com.example.bbsmodule.entity.response.CommitCommentResponse;
import com.example.bbsmodule.entity.result.CommitCommentResult;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.repository.BBSRepository;
import com.example.bbsmodule.repository.CommitCommentRepository;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.MD5CodeCeator;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Comment;
import com.shay.baselibrary.dto.Picture;
import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.params.UpLoadPicParam;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;
import com.shay.baselibrary.dto.result.UploadPicResult;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.baselibrary.picUtils.LoadLocalPic;

import java.io.FileNotFoundException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

public class CommitCommentViewModel extends ViewModel {


    private CommitCommentRepository commitCommentRepository;
    private MutableLiveData<List<PostPicInfo>> commitPicListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UploadPicResult> uploadPicResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CommitCommentResult> commitCommentResultMutableLiveData = new MutableLiveData<>();

    private List<Picture> succeedPic = new ArrayList<>();
    private List<PostPicInfo> selectPicList = new ArrayList<>();
    private CommitCommentParam currentCommentParam = new CommitCommentParam();

    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private UploadPicAsyncTask uploadPicAsyncTask;
    private  CommitCommentAsyncTask commitCommentAsyncTask;

    public MutableLiveData<UploadPicResult> getUploadPicResultMutableLiveData() {
        return uploadPicResultMutableLiveData;
    }

    public MutableLiveData<CommitCommentResult> getCommitCommentResultMutableLiveData() {
        return commitCommentResultMutableLiveData;
    }

    public MutableLiveData<List<PostPicInfo>> getCommitPicListMutableLiveData() {
        return commitPicListMutableLiveData;
    }

    public CommitCommentViewModel(CommitCommentRepository commitCommentRepository) {
        this.commitCommentRepository = commitCommentRepository;
    }


    public void selectPic(List<Uri> mSelected) {

        for(Uri uri:mSelected){
            PostPicInfo postPicInfo = new PostPicInfo();
            String realPath = LoadLocalPic.getRealPathFromUri(AppContext.getContext(), uri);
            postPicInfo.setUri(realPath);
            postPicInfo.setPicture(new Picture(){{setPicName(MD5CodeCeator.randomUUID()+".jpg");}});
            selectPicList.add(postPicInfo);
        }

        commitPicListMutableLiveData.setValue(selectPicList);
    }


    public int mContentListLength(){
        return selectPicList.size();
    }

    public void setPicFailed(int index){
        selectPicList.get(index).setFailed(true);
        commitPicListMutableLiveData.setValue(selectPicList);
    }

    public void setPicSucceed(int index){
        getSelectPicList().get(index).setSucceed(true);
        getSelectPicList().get(index).setFailed(false);
        commitPicListMutableLiveData.setValue(getSelectPicList());
    }


    public List<PostPicInfo> getSelectPicList() {
        return selectPicList;
    }

    public void uploadPic(int position){
        UpLoadPicParam picParam = new UpLoadPicParam();
        PostPicInfo postPicInfo = getSelectPicList().get(position);
        picParam.setUri(postPicInfo.getUri());
        picParam.setIndex(position);
        uploadPicAsyncTask = (UploadPicAsyncTask) asyncTaskFactory.createAsyncTask(new UploadPicAsyncTask());
        uploadPicAsyncTask.execute(picParam);
    }

    public void isPicAllIsSucceed(){
        for(PostPicInfo postPicInfo:getSelectPicList()){
            if(!postPicInfo.isSucceed()){
                return;
            }
        }

        //生成succeed list
        for(PostPicInfo picInfo:getSelectPicList()){
            Picture picture = picInfo.getPicture();
            succeedPic.add(picture);
        }

        currentCommentParam.setPicList(succeedPic);
        post(currentCommentParam);

        /***

         * */
    }

    public void removePic(int position){
        selectPicList.remove(position);
        commitPicListMutableLiveData.setValue(getSelectPicList());
    }


    public void sendComment(String postId,  String contentText) {
        currentCommentParam.setPostId(postId);
        currentCommentParam.setCommentText(contentText);

        if(mContentListLength() == 0){
            post(currentCommentParam);
            return;
        }

        for(int i = 0; i < mContentListLength(); i++){
            uploadPic(i);
        }


    }


    public void post(CommitCommentParam param){

        commitCommentAsyncTask = (CommitCommentAsyncTask) asyncTaskFactory.createAsyncTask(new CommitCommentAsyncTask());
        commitCommentAsyncTask.execute(param);
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

    //upLOAD
    class UploadPicAsyncTask extends AsyncTask<UpLoadPicParam, Integer, Integer > {

        @Override
        protected Integer doInBackground(UpLoadPicParam... upLoadPicParams) {
            UpLoadPicParam upLoadPicParam = upLoadPicParams[0];
            try {
                commitCommentRepository.uploadPic(upLoadPicParam, result -> {
                    UploadPicResult picResult = new UploadPicResult();
                    if(result instanceof Result.Success){
                        UpLoadPicResponse response = (UpLoadPicResponse) ((Result.Success) result).getData();
                        int index = response.getIndex();
                        Picture picture = new Picture();
                        picture.setPicName(response.getPicName());
                        getSelectPicList().get(index).setPicture(picture);
                        picResult.setIndex(index);
                    }else {
                        String error = ((Result.Error) result).getErrorMsg();
                        picResult.setErrorMsg(error);
                    }
                    uploadPicResultMutableLiveData.setValue(picResult);
                });

            }catch (FileNotFoundException e){
                e.printStackTrace();
                publishProgress(upLoadPicParam.getIndex());
            }
            catch (Exception e){
                e.printStackTrace();
                return upLoadPicParam.getIndex();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            uploadPicResultMutableLiveData.setValue(new UploadPicResult(){
                {
                    setErrorMsg("没有找到图片");
                    setIndex(values[0]);
                }
            });
        }

        @Override
        protected void onPostExecute(Integer index) {
            if(index != null){
                uploadPicResultMutableLiveData.setValue(new UploadPicResult(){{setErrorMsg("上传出错");setIndex(index);}});
            }

        }
    }

    //commitcomment
    class CommitCommentAsyncTask extends AsyncTask<CommitCommentParam, String, Exception>{

        @Override
        protected Exception doInBackground(CommitCommentParam... commitCommentParams) {
            CommitCommentParam commitCommentParam = commitCommentParams[0];

            try {
                commitCommentRepository.commitComment(commitCommentParam, new CommitCommentRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        CommitCommentResult commitCommentResult = new CommitCommentResult();
                        if(result instanceof Result.Success){

                        }else {
                            String error = ((Result.Error) result).getErrorMsg();
                            commitCommentResult.setErrorMsg(error);
                        }

                        commitCommentResultMutableLiveData.setValue(commitCommentResult);
                    }
                });
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }

        }

        @Override
        protected void onPostExecute(Exception e) {
            if(e != null){
                commitCommentResultMutableLiveData.setValue(new CommitCommentResult(){{setErrorMsg("应用出错");}});
            }

        }

    }
}
