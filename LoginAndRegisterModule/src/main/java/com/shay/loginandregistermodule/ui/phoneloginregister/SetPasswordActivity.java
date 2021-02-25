package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.R2;
import com.shay.loginandregistermodule.data.entity.result.SetPwResult;
import com.shay.loginandregistermodule.viewmodel.SetPasswordFactory;
import com.shay.loginandregistermodule.viewmodel.SetPasswordViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 其他模块调用该验证码模块后会以intent附带字段为result的boolean结果
 */
@Route(path = AroutePath.SetPassActivity)
public class SetPasswordActivity extends AppCompatActivity {

    @BindView(R2.id.activtiy_set_pw_frist_et)
     TextInputEditText firstEt;
    @BindView(R2.id.activtiy_set_pw_second_et)
     TextInputEditText secondEt;
    @BindView(R2.id.activtiy_set_password_confrim_btn)
    QMUIRoundButton confrimBtn;

    Unbinder unbinder;

    public String phone_token;

    private SetPasswordViewModel setPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        setPasswordViewModel = new ViewModelProvider(this, new SetPasswordFactory())
                .get(SetPasswordViewModel.class);

        phone_token = getIntent().getStringExtra(AroutePath.paramName.PHONE_TOKEN_PARAM_NAME);
        init();
        initObserver();
        initListener();
    }


    private void init(){

    }

    public void initObserver(){

        setPasswordViewModel.getSetPwResultMutableLiveData().observe(this, new Observer<SetPwResult>() {
            @Override
            public void onChanged(SetPwResult setPwResult) {
                if(TextUtils.isEmpty(setPwResult.getErrorMsg())){
                    ToastUntil.showToast(setPwResult.getErrorMsg(), AppContext.getContext());
                }else {
                    Intent intent = new Intent();
                    intent.putExtra(AroutePath.paramName.SET_PW_RESULT_PARAM_NAME, true);
                    setResult(AroutePath.resultCode.SET_PW_RESULT_CODE , intent);
                    finish();
                }
            }
        });
    }

    public void initListener(){

        TextWatcher SecondPwTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 6){

                    confrimBtn.setEnabled(false);
                    return;
                }

                if(TextUtils.isEmpty(s.toString())){
                    confrimBtn.setEnabled(false);
                    return;
                }

                if(s.toString().equals(firstEt.getText().toString())){
                    confrimBtn.setEnabled(true);
                }else {
                    confrimBtn.setEnabled(false);
                    secondEt.setError("两次密码不一致");
                }

            }
        };

        TextWatcher fristTextWatch = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(TextUtils.isEmpty(s.toString())){
                    confrimBtn.setEnabled(false);
                    return;
                }
                if(s.length() < 6){
                    firstEt.setError("必须大于6位");
                    confrimBtn.setEnabled(false);
                }
            }
        };

        //textWatch
        firstEt.addTextChangedListener(fristTextWatch);
        secondEt.addTextChangedListener(SecondPwTextWatcher);

        //btn
        confrimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = firstEt.getText().toString().trim();
                if(!pw.isEmpty()){
                    setPasswordViewModel.confirm(pw, phone_token);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        setPasswordViewModel.cancelAsyncTask();
    }
}
