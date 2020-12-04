package com.shay.loginandregistermodule.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.LoginRepository;
import com.shay.loginandregistermodule.data.Result;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.ui.login.LoggedInUserView;
import com.shay.loginandregistermodule.ui.login.LoginFormState;
import com.shay.loginandregistermodule.ui.login.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    //private LoginAsyncTask loginAsyncTask;


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }



    class LoginAsyncTask extends AsyncTask<HashMap<String, Object>, Integer, Result >{


        @Override
        protected Result doInBackground(HashMap<String, Object>... hashMaps) {

            HashMap<String, Object> map = hashMaps[0];



            return null;
        }

    }

    public void login(String account, String password) {
        // can be launched in a separate asynchronous job
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("account", account);
        hashMap.put("password", password);

        //新建并存入工厂的list中
        loginRepository.login(hashMap,
                new LoginRepository.ResultListener() {//执行于rxjava的observer线程
                    @Override
                    public void returnResult(Result result) {
                        if (result instanceof Result.Success) {
                            TestUser data = ((Result.Success<TestUser>) result).getData();
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUserName())));
                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    }
                });
      /*  try {
            loginAsyncTask = (LoginAsyncTask) asyncTaskFactory.createAsyncTask(LoginAsyncTask.class);
            loginAsyncTask.execute(hashMap);
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/

    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }



    public void cancelAsyncTask(){

        if(asyncTaskFactory.getAsyncTaskArrayList().isEmpty()){
            return;
        }else {
            ArrayList<AsyncTask> arrayList = asyncTaskFactory.getAsyncTaskArrayList();
            for (AsyncTask asyncTask:arrayList){
                if(asyncTask == null){
                    continue;
                }else {
                    asyncTask.cancel(true);
                    asyncTask = null;
                }
            }

            arrayList.clear();
        }
    }
}
