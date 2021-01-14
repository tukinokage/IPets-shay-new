package com.example.bbsmodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.entity.params.GetPostListParam;
import com.example.bbsmodule.entity.response.GetPostListResponse;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.repository.BBSRepository;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.enums.PostTypeEnum;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

import java.util.ArrayList;
import java.util.List;

public class BBSViewModel extends ViewModel {
    private BBSRepository bbsRepository;
    private MutableLiveData<GetPostListResult> postListResultMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<String>> selectonListMutableLiveData = new MutableLiveData<>();
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    SearchPostListAsyncTask searchPostListAsyncTask;

    public MutableLiveData<GetPostListResult> getPostListResultMutableLiveData() {
        return postListResultMutableLiveData;
    }
    public MutableLiveData<List<String>> getSelectonListMutableLiveData() {
        return selectonListMutableLiveData;
    }
    class SearchPostListAsyncTask extends AsyncTask<GetPostListParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetPostListParam... getPostListParams) {

            try {
                GetPostListParam getPostListParam = getPostListParams[0];
                bbsRepository.getPostList(getPostListParam, new BBSRepository.GetResultListener() {
                    @Override
                    public void getResult(Result result) {
                        GetPostListResult getPostListResult = new GetPostListResult();
                        if(result instanceof Result.Success){
                          GetPostListResponse response = (GetPostListResponse) ((Result.Success) result).getData();
                          getPostListResult.setBbsPostList(response.getBbsPostList());
                          getPostListResult.setHasMore(response.isHasMore());
                        }else {
                            String error = ((Result.Error) result).getErrorMsg();
                            getPostListResult.setErrorMg(error);
                        }

                        getPostListResultMutableLiveData().setValue(getPostListResult);
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
                getPostListResultMutableLiveData().setValue(new GetPostListResult(){{setErrorMg("应用出错");}});
            }
        }
    }

    public BBSViewModel(BBSRepository bbsRepository) {
        this.bbsRepository = bbsRepository;
    }

    public void getBBSPostLIst(int type, String searchCondition, int perPaperNum, int currentPaperNum){
        GetPostListParam getPostListParam = new GetPostListParam();
        getPostListParam.setCurrentPaperNum(currentPaperNum);
        getPostListParam.setPerPaperNum(perPaperNum);
        getPostListParam.setSearchCondition(searchCondition);
        getPostListParam.setType(type);
        searchPostListAsyncTask = (SearchPostListAsyncTask) asyncTaskFactory
                .createAsyncTask(new SearchPostListAsyncTask());

        searchPostListAsyncTask.execute(getPostListParam);
    }

    public void getSelectionList(){
        List<String> list = new ArrayList<>();
        for (PostTypeEnum e:
             PostTypeEnum.values()) {
            list.add(e.getRankName());
        }
        selectonListMutableLiveData.setValue(list);

    }

    public void cancelAsyTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
