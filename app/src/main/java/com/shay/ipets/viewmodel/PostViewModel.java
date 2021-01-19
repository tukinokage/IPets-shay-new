package com.shay.ipets.viewmodel;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.MD5CodeCeator;
import com.shay.baselibrary.dto.Picture;
import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.baselibrary.picUtils.LoadLocalPic;
import com.shay.ipets.entity.params.PostParam;
import com.shay.baselibrary.dto.params.UpLoadPicParam;
import com.shay.baselibrary.dto.response.UpLoadPicResponse;
import com.shay.ipets.entity.result.PostResult;
import com.shay.baselibrary.dto.result.UploadPicResult;
import com.shay.ipets.repository.PostRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {
    private List<Picture> succeedPic = new ArrayList<>();
    private List<PostPicInfo> selectPicList = new ArrayList<>();
    private PostParam currentPostParam;

    public List<PostPicInfo> getSelectPicList() {
        return selectPicList;
    }

    private String currentPostId;
    private PostRepository postRepository;

    private MutableLiveData<PostResult> postResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UploadPicResult> uploadPicResultMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<PostPicInfo>> postPicInfoMutableLiveData = new MutableLiveData<>();
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private PostAsyncTask postAsyncTask;
    UploadPicAsyncTask uploadPicAsyncTask;

    public MutableLiveData<List<PostPicInfo>> getPostPicInfoMutableLiveData() {
        return postPicInfoMutableLiveData;
    }
    public MutableLiveData<PostResult> getPostResultMutableLiveData() {
        return postResultMutableLiveData;
    }

    public MutableLiveData<UploadPicResult> getUploadPicResultMutableLiveData() {
        return uploadPicResultMutableLiveData;
    }
    public String getCurrentPostId() {
        return currentPostId;
    }

    class PostAsyncTask extends AsyncTask<PostParam, String, Exception>{

        @Override
        protected Exception doInBackground(PostParam... postParams) {
            PostParam postParam = postParams[0];
            try {
                postRepository.post(postParam, result -> {
                    PostResult postResult = new PostResult();
                    if(result instanceof Result.Success){

                    }else {
                        String error = ((Result.Error) result).getErrorMsg();
                        postResult.setErrorMsg(error);
                    }
                    postResultMutableLiveData.setValue(postResult);
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

        class UploadPicAsyncTask extends AsyncTask<UpLoadPicParam, String, Integer >{

            @Override
            protected Integer doInBackground(UpLoadPicParam... upLoadPicParams) {
                UpLoadPicParam upLoadPicParam = upLoadPicParams[0];
                try {
                    postRepository.uploadPic(upLoadPicParam, new PostRepository.GetResultListener() {
                        @Override
                        public void getResult(Result result) {
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
                        }
                    });

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                    uploadPicResultMutableLiveData.setValue(new UploadPicResult(){
                        {
                            setErrorMsg("没有找到图片");
                            setIndex(upLoadPicParam.getIndex());
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                    return upLoadPicParam.getIndex();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer index) {
                if(index != null){
                    uploadPicResultMutableLiveData.setValue(new UploadPicResult(){{setErrorMsg("上传出错");setIndex(index);}});
                }

            }
        }

    public PostViewModel(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.currentPostId = MD5CodeCeator.randomUUID16();
    }

    public void post(PostParam postParam){
        postAsyncTask = (PostAsyncTask) asyncTaskFactory.createAsyncTask(new PostAsyncTask());
        postAsyncTask.execute(postParam);
    }

    public void uploadPic(int position){
        UpLoadPicParam picParam = new UpLoadPicParam();
        PostPicInfo postPicInfo = getSelectPicList().get(position);
        picParam.setUri(postPicInfo.getUri());
        picParam.setIndex(position);
        uploadPicAsyncTask = (UploadPicAsyncTask) asyncTaskFactory.createAsyncTask(new UploadPicAsyncTask());
        uploadPicAsyncTask.execute(picParam);
    }

   public void submitAll(String title, String ContentText, int type){
       PostParam postParam = new PostParam();

       postParam.setTitle(title);
       postParam.setContentText(ContentText);
       postParam.setType(type);
       currentPostParam = postParam;

        if( getSelectPicList().size() == 0){
            post(currentPostParam);
        }else {
            for(int i = 0; i < getSelectPicList().size(); i++){
                uploadPic(i);
            }
        }

   }



   public int mContentListLength(){
       return selectPicList.size();
   }

    public void selectPic(List<Uri> list){
        for(Uri uri:list){
            PostPicInfo postPicInfo = new PostPicInfo();
            String realPath = LoadLocalPic.getRealPathFromUri(AppContext.getContext(), uri);
            postPicInfo.setUri(realPath);
            postPicInfo.setPicture(new Picture(){{setPicName(MD5CodeCeator.randomUUID()+".jpg");}});
            selectPicList.add(postPicInfo);
        }

        getPostPicInfoMutableLiveData().setValue(selectPicList);
    }

    public void setPicSucceed(int index){
        getSelectPicList().get(index).setSucceed(true);
        getSelectPicList().get(index).setFailed(false);
        postPicInfoMutableLiveData.setValue(getSelectPicList());
    }

    public void setPicFailed(int index){
        getSelectPicList().get(index).setFailed(true);
        postPicInfoMutableLiveData.setValue(getSelectPicList());
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

        currentPostParam.setPicList(succeedPic);
        post(currentPostParam);

        /***

        * */
    }



    public void removePic(int position){
        selectPicList.remove(position);
        postPicInfoMutableLiveData.setValue(getSelectPicList());
    }


    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }

}
