package com.shay.loginandregistermodule.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.SPUserInfo;
import com.shay.baselibrary.dto.TestUser;
import com.shay.baselibrary.factorys.AsyncTaskFactory;
import com.shay.loginandregistermodule.data.entity.params.CheckPhExistParam;
import com.shay.loginandregistermodule.data.entity.responsedata.CheckPhoneRepData;
import com.shay.loginandregistermodule.data.entity.result.PhoneLoginResult;
import com.shay.loginandregistermodule.data.repository.LoginRepository;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.ui.login.LoggedInUserView;
import com.shay.loginandregistermodule.ui.login.LoginFormState;
import com.shay.loginandregistermodule.ui.login.LoginResult;

import java.util.HashMap;

//LiveData的setValue只能在主线程调用，使用rxjava回调时注意在设置在主线程
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    private MutableLiveData<PhoneLoginResult> phoneLoginResult = new MutableLiveData<>();

    private LoginRepository loginRepository;
    private AsyncTaskFactory asyncTaskFactory = new AsyncTaskFactory();
    private LoginAsyncTask loginAsyncTask;
    private CheckPhoneAsyncTask checkPhoneAsyncTask;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<PhoneLoginResult> getPhoneLoginResult() { return phoneLoginResult; }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public class LoginAsyncTask extends AsyncTask<HashMap<String, Object>, Integer, Exception>{

        @Override
        protected Exception doInBackground(HashMap<String, Object>... hashMaps) {

            HashMap<String, Object> map = hashMaps[0];
            //执行于rxjava的observer线程
            //生成客户端是一个线程（在asyncxtask线程），rxjava + retrofit只是在访问网络以及响应时使用了另一个异步线程
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }

        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if(e != null){
               e.printStackTrace();
               loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }

    public class CheckPhoneAsyncTask extends AsyncTask<CheckPhExistParam, String, Exception>{

        @Override
        protected Exception doInBackground(CheckPhExistParam... checkPhExistParams) {
            Gson gson = new Gson();
            String json = gson.toJson(checkPhExistParams[0]);
            HashMap<String, Object> param = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());

            try {
                loginRepository.checkPhone(param, new LoginRepository.ResultListener() {
                    @Override

                    //主线程
                    public void returnResult(Result result) {
                        if (result instanceof Result.Success) {
                            CheckPhoneRepData data = (CheckPhoneRepData) ((Result.Success) result).getData();
                            saveUserInfo(data.getToken(), data.getUserId(), data.getUserName());

                            phoneLoginResult.setValue(new PhoneLoginResult(){{setType(data.getUserType()); }});
                        } else {
                            PhoneLoginResult phoneResult = new PhoneLoginResult();
                            result = (Result.Error)result;
                            phoneResult.setErrorMsg(((Result.Error) result).getErrorMsg());
                            phoneLoginResult.setValue(phoneResult);
                        }
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
            super.onPostExecute(e);
            if( e != null){
                phoneLoginResult.setValue(new PhoneLoginResult(){{setErrorMsg("应用出错");}});
            }
        }
    }

    //不存在就会新建账号，并要求设置密码
    public void CheckPhoneIsExist(String phoneToken){
        CheckPhExistParam checkPhExistParam = new CheckPhExistParam();
        checkPhExistParam.setPhoneToken(phoneToken);
        checkPhoneAsyncTask = (CheckPhoneAsyncTask) asyncTaskFactory.createAsyncTask(new CheckPhoneAsyncTask());
        checkPhoneAsyncTask.execute(checkPhExistParam);
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

    //缓存用户信息
    public void saveUserInfo(String token, String userId, String userName){
        SPUserInfo spUserInfo = new SPUserInfo();
        spUserInfo.setToken(token);
        spUserInfo.setUserId(userId);
        spUserInfo.setUserName(userName);
        loginRepository.saveUserInfo(spUserInfo, new LoginRepository.ResultListener() {
            @Override
            public void returnResult(Result result) {
                if(result instanceof Result.Success){

                }else {

                }
            }
        });
    }

    public void cancelAsyncTask(){
        asyncTaskFactory.cancelAsyncTask();
    }
}
