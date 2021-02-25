package com.shay.loginandregistermodule.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.loginandregistermodule.R2;
import com.shay.loginandregistermodule.data.entity.result.PhoneLoginResult;
import com.shay.loginandregistermodule.data.entity.result.SPSaveUserResult;
import com.shay.loginandregistermodule.ui.phoneloginregister.PhoneCheckActivity;
import com.shay.loginandregistermodule.ui.phoneloginregister.SetPasswordActivity;
import com.shay.loginandregistermodule.viewmodel.LoginViewModel;
import com.shay.loginandregistermodule.viewmodel.LoginViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.LoginActivity)
public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    @BindView(R2.id.login_activity_phone_linearLayout)
    LinearLayout phoneLayout;
    @BindView(R2.id.login_activity_weibo_linearLayout)
    LinearLayout weiboLayout;
    @BindView(R2.id.login_activity_go_register_tv)
    TextView backTextView;
    @BindView(R2.id.login_activity_account_et)
    EditText usernameEditText ;
    @BindView(R2.id.login_activity_password_tv)
    EditText passwordEditText ;
    @BindView(R2.id.login_activity_login_bt)
    Button loginButton;
    @BindView(R2.id.login_activity_loading_pb)
    ProgressBar loadingProgressBar;

    private String phoneToken = "";
    public final static int REQUEST_CODE_PHONE = 1;
    public final static int REQUEST_CODE_SET_PWD = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        initListener();
        initObserver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_PHONE && resultCode == AroutePath.resultCode.PHONE_RESULT_CODE){
            ConfrimPhoneResult confrimPhoneResult = (ConfrimPhoneResult) data.getExtras().get(AroutePath.paramName.RESULT_PARAM_NAME);
            //检查手机号码是否注册，存在就登录，不存在注册，并跳转设置密码
            loginViewModel.CheckPhoneIsExist(confrimPhoneResult.getPhoneToken());
            phoneToken = confrimPhoneResult.getPhoneToken();
            Log.d("phoneToken:", confrimPhoneResult.getPhoneToken());
        }else if(requestCode == REQUEST_CODE_SET_PWD && resultCode == AroutePath.resultCode.SET_PW_RESULT_CODE){
            boolean result = (boolean) data.getExtras().get(AroutePath.paramName.SET_PW_RESULT_PARAM_NAME);
            if(result){
                /**设置密码成功*/
                /**跳转到主界面**/
                ARouter.getInstance().build(AroutePath.MainActivity).navigation();
            }
        }
    }

    private void initObserver(){

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });


        loginViewModel.getSpSaveUserResultMutableLiveData().observe(this, new Observer<SPSaveUserResult>() {
            @Override
            public void onChanged(SPSaveUserResult spSaveUserResult) {
                if(TextUtils.isEmpty(spSaveUserResult.getErrorMsg())){
                    ToastUntil.showToast("＼＼登录成功／／", AppContext.getContext() );
                    setResult(Activity.RESULT_OK);
                }else {
                    ToastUntil.showToast("信息保存失败，重新登陆吧", AppContext.getContext() );
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (TextUtils.isEmpty(loginResult.getErrorMsg())) {
                    loginViewModel.saveUserInfo(loginResult.token, loginResult.userId, loginResult.userName);
                    ToastUntil.showToast("欢迎" + loginResult.userName, AppContext.getContext() );
                    ARouter.getInstance().build(AroutePath.MainActivity).navigation();
                }else {
                    ToastUntil.showToast(loginResult.errorMsg, AppContext.getContext() );
                }

               // setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                // finish();
            }
        });


        //手机登录注册请求后
        loginViewModel.getPhoneLoginResult().observe(this, new Observer<PhoneLoginResult>() {
            @Override
            public void onChanged(PhoneLoginResult phoneLoginResult) {
                if(TextUtils.isEmpty(phoneLoginResult.getErrorMsg())){
                    if(phoneLoginResult.getType() == 0){
                        //新用户
                        //跳转到设置密码
                        /*Intent intent = new Intent(LoginActivity.this, SetPasswordActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SET_PWD);*/
                        Postcard postcard = ARouter.getInstance().build(AroutePath.SetPassActivity);
                        LogisticsCenter.completion(postcard);
                        Intent intent = new Intent(LoginActivity.this, postcard.getDestination());
                        intent.putExtra(AroutePath.paramName.PHONE_TOKEN_PARAM_NAME, phoneToken);
                        startActivityForResult(intent, AroutePath.requestCode.REQUEST_CODE_SET_PWD);

                    }else if(phoneLoginResult.getType() == 1){
                        //老用户
                        //跳转主界面
                        ARouter.getInstance().build(AroutePath.MainActivity).navigation();
                    }
                }else {
                    ToastUntil.showToast(phoneLoginResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });
    }

    private void initListener(){
        weiboLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneCheckActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PHONE);
            }
        });

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        /*********************textwatch***************/
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        /*************************************/
    }

    @Override
    protected void onDestroy() {
        loginViewModel.cancelAsyncTask();
        super.onDestroy();
    }
}
