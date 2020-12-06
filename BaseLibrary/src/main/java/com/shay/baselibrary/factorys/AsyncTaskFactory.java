package com.shay.baselibrary.factorys;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AsyncTaskFactory<T extends AsyncTask> {
    private ArrayList<AsyncTask> asyncTaskArrayList = new ArrayList<>();

    public ArrayList<AsyncTask> getAsyncTaskArrayList() {
        return asyncTaskArrayList;
    }


    public AsyncTask createAsyncTask(AsyncTask asyncTask)  {
       asyncTaskArrayList.add(asyncTask);
       return asyncTask;
    }

    public void cancelAsyncTask(){
        if(asyncTaskArrayList.isEmpty()){
            return;
        }else {
            for (AsyncTask asyncTask:asyncTaskArrayList){
                if(asyncTask == null){
                    continue;
                }else {
                    asyncTask.cancel(true);
                    //asyncTask = null;
                }
            }

            asyncTaskArrayList.clear();
        }
    }
}
