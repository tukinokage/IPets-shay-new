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

import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.data.entity.result.SetPwResult;
import com.shay.loginandregistermodule.viewmodel.SetPasswordFactory;
import com.shay.loginandregistermodule.viewmodel.SetPasswordViewModel;

import butterknife.BindView;

/***
 * 其他模块调用该验证码模块后会以intent附带字段为result的boolean结果
 */
public class SetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.activtiy_set_pw_frist_et)
     TextInputEditText firstEt;
    @BindView(R.id.activtiy_set_pw_second_et)
     TextInputEditText secondEt;
    @BindView(R.id.activtiy_set_password_confrim_btn)
    QMUIRoundButton confrimBtn;

    public static final int RESULT_CODE = 1002;
    public static final String RESULT_PARAM_NAME = "result";
    private SetPasswordViewModel setPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        setPasswordViewModel = new ViewModelProvider(this, new SetPasswordFactory())
                .get(SetPasswordViewModel.class);

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
                    intent.putExtra(RESULT_PARAM_NAME, true);
                    setResult(RESULT_CODE, intent);
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
                    setPasswordViewModel.confirm(pw);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
