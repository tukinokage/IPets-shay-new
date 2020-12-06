package com.shay.loginandregistermodule.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.LoginRepository;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.ui.login.LoggedInUserView;
import com.shay.loginandregistermodule.ui.login.LoginFormState;
import com.shay.loginandregistermodule.ui.login.LoginResult;

import java.util.HashMap;

//LiveData的setValue只能在主线程调用，使用rxjava回调时注意在设置在主线程
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private LoginAsyncTask loginAsyncTask;


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public class LoginAsyncTask extends AsyncTask<HashMap<String, Object>, Integer, Result>{

        @Override
        protected Result doInBackground(HashMap<String, Object>... hashMaps) {

            HashMap<String, Object> map = hashMaps[0];
            //执行于rxjava的observer线程
            //生成客户端是一个线程（在asyncxtask线程），rxjava + retrofit只是在访问网络以及响应时使用了另一个异步线程
            loginRepository.login(map,
                    result -> {
                    /********rxjava回调主线程的监听器***********/

                        if (result instanceof Result.Success) {
                            TestUser data = ((Result.Success<TestUser>) result).getData();
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUserName())));
                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    }
                    /******************************************/
                    );
            return null;
        }

    }

    public void login(String account, String password) {
        // can be launched in a separate asynchronous job
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", account);
        hashMap.put("password", password);

        //新建并存入工厂的list中

        loginAsyncTask = (LoginAsyncTask) asyncTaskFactory.createAsyncTask(new LoginAsyncTask());
        loginAsyncTask.execute(hashMap);


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
        asyncTaskFactory.cancelAsyncTask();
    }
}
