package com.shay.baselibrary.factorys;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AsyncTaskFactory<T extends AsyncTask> {
    private ArrayList<AsyncTask> asyncTaskArrayList = new ArrayList<>();

    public ArrayList<AsyncTask> getAsyncTaskArrayList() {
        return asyncTaskArrayList;
    }


    public T createAsyncTask(Class<T> clazz) throws InstantiationException, IllegalAccessException {
       T asyncTask =  clazz.newInstance();
       asyncTaskArrayList.add(asyncTask);
       return asyncTask;
    }
}
