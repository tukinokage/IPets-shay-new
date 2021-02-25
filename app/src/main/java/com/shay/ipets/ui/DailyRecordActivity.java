package com.shay.ipets.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.ipets.R;
import com.shay.ipets.entity.result.ConfirmRecordResult;
import com.shay.ipets.viewmodel.DaliRecordModel;
import com.shay.ipets.viewmodel.DaliyRecordViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyRecordActivity extends AppCompatActivity {

    @BindView(R.id.dail_record_text_input_et)
    TextInputEditText contentText;
    @BindView(R.id.daily_record_text_confrim_btn)
    Button confirmBtn;
   /* @BindView(R.id.daily_record_date_tv)
    TextView dateTv;*/
    @BindView(R.id.main_activity_go_register_tv)
    TextView backTv;

    Unbinder unbinder;
    DaliRecordModel daliRecordModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        unbinder = ButterKnife.bind(this);

        daliRecordModel = new ViewModelProvider(this, new DaliyRecordViewModelFactory())
                .get(DaliRecordModel.class);

        initObserver();
        initListener();
    }

    private void initObserver(){
        daliRecordModel.getConfirmRecordResult().observe(this, new Observer<ConfirmRecordResult>() {
            @Override
            public void onChanged(ConfirmRecordResult confirmRecordResult) {
                if(TextUtils.isEmpty(confirmRecordResult.getErrorMsg())){
                    ToastUntil.showToast("日志发布成功", DailyRecordActivity.this);
                    finish();
                }else {
                    ToastUntil.showToast(confirmRecordResult.getErrorMsg(), DailyRecordActivity.this);
                }
            }
        });
    }

    private void initListener(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(contentText.getText().toString().trim())){
                    ToastUntil.showToast("请输入内容后再发布", AppContext.getContext());
                }else {
                    daliRecordModel.confirmDailyRecord(contentText.getText().toString());
                }
            }
        });

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
