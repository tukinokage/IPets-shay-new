package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.loginandregistermodule.R;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.loginandregistermodule.data.entity.result.SmsResultStauts;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModel;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


/***
 * 其他模块调用该验证码模块后会以intent附带字段为data的序列化对象confrimPhoneResult结果
 */

@Route(path = AroutePath.PhoneActivity)
public class PhoneCheckActivity extends AppCompatActivity {

    //字段
    public static final String REQUEST_TYPE="REQUEST_TYPE";

    //类型
    public static final String REQUEST_TYPE_LR="REQUEST_TYPE_LR";
    public static final String REQUEST_TYPE_UPDATE_PWD="REQUEST_TYPE_UPDATE_PWD";
    public static final String REQUEST_TYPE_UPDATE_PHONE="REQUEST_TYPE_UPDATE_PHONE";

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

    AsyncTask countTimeAsyncTask;

    //短信是否发送成功
    boolean sendMsgStatus = false;
    String rightCode = "";
    String phoneNum;

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

        initListenser();
        initObserver();
    }

    @SuppressLint("StaticFieldLeak")
    public void msgConfirm(View v){

        phoneNum = phoneNumEt.getText().toString().trim();
        phoneSmsViewModel.sendSms(phoneNum);
        submitSmsBtn.setEnabled(false);
        countTimeAsyncTask = new AsyncTask<Integer, Integer, Integer>(){

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

    //确定键监听事件
    public void confrim(View view){
        String inputCode = sgmCodeEt.getText().toString().trim();

        if(TextUtils.isEmpty(inputCode)){
            ToastUntil.showToast("请输入正确验证码！", AppContext.getContext());
           return;
        }
        if (TextUtils.isEmpty(rightCode)){
            return;
        }

        if (TextUtils.isEmpty(phoneNum)){
            ToastUntil.showToast("请输入正确的手机号码", AppContext.getContext());
            return;
        }

        if (!sendMsgStatus){
            return;
        }

        //根据上一个intent的操作要求进行下一activity操作
        if(rightCode.equals(inputCode)){
            rightCode = "";
            sendMsgStatus = false;
            ToastUntil.showToast("验证码正确", AppContext.getContext());

            //getIntent().getStringExtra(REQUEST_TYPE);

            /**
             * 正确操作
            * */
            phoneSmsViewModel.confrimPhone(phoneNum, inputCode);

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


    //初始化observer
    public void initObserver(){
        phoneSmsViewModel.getSmsResultLiveData().observe(this, new Observer<SmsResultStauts>() {
            @Override
            public void onChanged(SmsResultStauts smsResultStauts) {
                if(TextUtils.isEmpty(smsResultStauts.getErrorMsg())){
                    rightCode = smsResultStauts.getScertCode();
                    sendMsgStatus = true;
                }else {
                    ToastUntil.showToast(smsResultStauts.getErrorMsg(), AppContext.getContext());
                    sendMsgStatus = false;
                }
            }
        });

        phoneSmsViewModel.getConfrimPhoneResultMutableLiveData().observe(this, new Observer<ConfrimPhoneResult>() {
            @Override
            public void onChanged(ConfrimPhoneResult confrimPhoneResult) {
                if(TextUtils.isEmpty(confrimPhoneResult.getErrorMsg())){

                    //返回数据给上一activity
                    Intent intent = new Intent();
                    intent.putExtra(AroutePath.paramName.RESULT_PARAM_NAME, confrimPhoneResult);
                    setResult(AroutePath.resultCode.PHONE_RESULT_CODE, intent);
                    finish();
                }else {
                    ToastUntil.showToast(confrimPhoneResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        phoneSmsViewModel.cancelAsyncTask();
        countTimeAsyncTask.cancel(true);
        super.onDestroy();
    }


}
