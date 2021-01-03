package com.shay.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.ipets.viewmodel.DaliRecordModel;
import com.shay.ipets.viewmodel.DaliyRecordViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyRecordActivity extends AppCompatActivity {

    @BindView(R.id.dail_record_text_input_et)
    TextInputEditText contentText;
    @BindView(R.id.daily_record_text_confrim_btn)
    Button confirmBtn;
    @BindView(R.id.daily_record_date_tv)
    TextView dateTv;
    @BindView(R.id.main_activity_go_register_tv)
    TextView backTv;
    DaliRecordModel daliRecordModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        ButterKnife.bind(this);

        daliRecordModel = new ViewModelProvider(this, new DaliyRecordViewModelFactory())
                .get(DaliRecordModel.class);
    }

    private void initObserver(){

    }

    private void initListener(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(contentText.getText().toString().trim())){
                    ToastUntil.showToast("请输入内容后再发布", AppContext.getContext());
                }else {

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


}
