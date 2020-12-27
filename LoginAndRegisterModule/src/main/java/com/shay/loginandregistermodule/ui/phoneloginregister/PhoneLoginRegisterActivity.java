package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.data.entity.SmsResultStauts;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModel;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneLoginRegisterActivity extends AppCompatActivity {

    private PhoneSmsViewModel phoneSmsViewModel;
    @BindView(R.id.activtiy_phone_login_register_msg_submit_btn)
    Button submitSmsBtn;
    @BindView(R.id.activtiy_phone_login_register_confrim_btn)
    Button confrimBtn;
    @BindView(R.id.activtiy_phone_login_register_smsCode_et)
    EditText sgmCodeEt;
    @BindView(R.id.activtiy_phone_login_register_phoneNum_et)
    EditText phoneNumEt;
    @BindView(R.id.login_activity_go_register_tv)
    TextView backTextView;
    @BindView(R.id.activtiy_phone_login_register_phonetip_tv)
    TextView phoneTipTv;

    //短信是否发送成功
    boolean sendMsgStatus = false;
    String rightCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login_register);

        ButterKnife.bind(this);
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
                } else {
                    ToastUntil.showToast(smsResultStauts.getErrorMsg(), AppContext.getContext());
                }
            }
        });
        initListenser();
    }

    @SuppressLint("StaticFieldLeak")
    public void msgConfirm(View v){

        String phoneNum = phoneNumEt.getText().toString().trim();
        phoneSmsViewModel.sendSms(phoneNum);
        submitSmsBtn.setEnabled(false);
        new AsyncTask<Integer, Integer, Integer>(){

            @Override
            protected Integer doInBackground(Integer... integers) {
                long ctime = 1000 * integers[0];
                long mtime = 0;
                while (mtime < ctime){
                    try {
                        Thread.sleep(1000);
                        mtime += 1000;
                        publishProgress((int) ((ctime - mtime)/1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                submitSmsBtn.setText( values[0] + "秒");
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                submitSmsBtn.setText("发送");
                submitSmsBtn.setEnabled(true);
            }
        }.execute(60);

    }

    public void confrim(View view){
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
        confrimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confrim(v);
            }
        });

        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phoneNumEt.getText().length() == 11){
                    phoneTipTv.setVisibility(View.INVISIBLE);
                }else {
                    phoneTipTv.setVisibility(View.VISIBLE);
                }
            }
        });

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        phoneSmsViewModel.cancelAsyncTask();
        super.onDestroy();
    }
}
