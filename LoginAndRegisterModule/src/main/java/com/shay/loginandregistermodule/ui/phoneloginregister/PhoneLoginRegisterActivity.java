package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.data.datasource.PhoneSmsDataSource;
import com.shay.loginandregistermodule.data.model.SmsResultStauts;
import com.shay.loginandregistermodule.data.repository.PhoneSmsRepository;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModel;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneLoginRegisterActivity extends AppCompatActivity {
    private PhoneSmsViewModel phoneSmsViewModel;

    @BindView(R.id.activtiy_phone_login_register_submit_btn)
    private Button submitBtn;
    @BindView(R.id.activtiy_phone_login_register_smsCode_et)
    private EditText sgmCodeEt;
    @BindView(R.id.activtiy_phone_login_register_phoneNum_et)
    private EditText phoneNumEt;

    boolean sendMsgStatus = false;
    String rightCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.activity_phone_login_register);
        phoneSmsViewModel = new ViewModelProvider(this, new PhoneSmsViewModelFactory())
                .get(PhoneSmsViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneSmsViewModel.getSmsResultLiveData().observe(this, new Observer<SmsResultStauts>() {
            @Override
            public void onChanged(SmsResultStauts smsResultStauts) {

                if(smsResultStauts.getErrorMsg() == null){
                    sendMsgStatus = true;
                    rightCode = smsResultStauts.getScertCode();
                }else {
                    ToastUntil.showToast(smsResultStauts.getErrorMsg(), AppContext.getContext());
                }
            }
        });

    }

    public void msgConfirm(View v){

        String phoneNum = phoneNumEt.getText().toString().trim();
        phoneSmsViewModel.sendSms(phoneNum);

        submitBtn.setEnabled(false);

    }

    public void confirm(View view){
        String inputCode = sgmCodeEt.getText().toString().trim();

        if(TextUtils.isEmpty(inputCode)){
           return;
        }
        if (TextUtils.isEmpty(rightCode)){
            return;
        }

        if (!sendMsgStatus){
            return;
        }

        //根据上一个intent的操作要求进行下一activity操作
        if(rightCode.equals(inputCode)){
            rightCode = "";
            sendMsgStatus = false;
            /**
             * 正确操作
            * */
        }else {
            ToastUntil.showToast("验证码错误", AppContext.getContext());
        }

    }


    private void initListenser(){
        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        phoneSmsViewModel.cancelAsyncTask();
        super.onDestroy();
    }
}
