package com.example.usermodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermodule.entity.params.GetUserCommentParam;
import com.example.usermodule.entity.responses.GetUserCommentResponse;
import com.example.usermodule.entity.result.GetUserCommentResult;
import com.example.usermodule.repository.UserInfoRepository;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.UserCommentItem;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.List;

public class GetUserCommentListViewModel extends ViewModel {
    UserInfoRepository userInfoRepository;
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    GetCommentAsyncTask getCommentAsyncTask;

    private List<UserCommentItem> commentItems;

    private MutableLiveData<List<UserCommentItem>> commentListMutableData = new MutableLiveData<>();



    private MutableLiveData<GetUserCommentResult>  userCommentResultMutableLiveData = new MutableLiveData<>();

    public GetUserCommentListViewModel(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        commentItems = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            Pet pet = new Pet();
//            pet.setPetName(String.valueOf(i));
//            pet.setViewNum(String.valueOf(50));
//            commentItems.add(pet);
//        }

    }

    public MutableLiveData<GetUserCommentResult> getUserCommentResultMutableLiveData() {
        return userCommentResultMutableLiveData;
    }
    public MutableLiveData<List<UserCommentItem>> getCommentListMutableData() {
        return commentListMutableData;
    }


    class GetCommentAsyncTask  extends AsyncTask<GetUserCommentParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetUserCommentParam... getUserCommentParams) {
            try {
                userInfoRepository.getCommentList(getUserCommentParams[0], new UserInfoRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetUserCommentResult getUserCommentResult = new GetUserCommentResult();
                        if(result instanceof Result.Error){
                            getUserCommentResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                        }else{
                            GetUserCommentResponse response = (GetUserCommentResponse) ((Result.Success)result).getData();
                            getUserCommentResult.setUserCommentItemList(response.getCommentItemList());
                        }
                        userCommentResultMutableLiveData.setValue(getUserCommentResult);
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
                userCommentResultMutableLiveData.setValue(new GetUserCommentResult() {{setErrorMsg("应用出错");}});
            }
        }
    }

    public void getCommentListData(String id, int perPagerCount, int currentPager){
        GetUserCommentParam param = new GetUserCommentParam();
        param.setCurrentPager(String.valueOf(currentPager));
        param.setPerPagerCount(String.valueOf(perPagerCount));
        param.setUserId(id);
        getCommentAsyncTask = (GetCommentAsyncTask) asyncTaskFactory.createAsyncTask(new GetCommentAsyncTask());
        getCommentAsyncTask.execute(param);
    }

    public List<UserCommentItem> getCurrentList(){
        return commentItems;
    }

    public void addCommentList(List list){
        commentItems.addAll(list);
        commentListMutableData.setValue(list);
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
