package com.example.bbsmodule.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbsmodule.entity.GetPostListParam;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.repository.BBSRepository;
import com.shay.baselibrary.factorys.AsyncTaskFactory;

public class BBSViewModel extends ViewModel {
    private BBSRepository bbsRepository;
    private MutableLiveData<GetPostListResult> postListResultMutableLiveData = new MutableLiveData<>();
    AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();

    public MutableLiveData<GetPostListResult> getPostListResultMutableLiveData() {
        return postListResultMutableLiveData;
    }
    class SearchPostListAsyncTask extends AsyncTask<GetPostListParam, String, Exception>{

        @Override
        protected Exception doInBackground(GetPostListParam... getPostListParams) {

            try {
                GetPostListParam getPostListParam = getPostListParams[0];

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
    }

    public void cancelAsyTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
