package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.shay.baselibrary.AroutePath;

import butterknife.ButterKnife;

@Route(path = AroutePath.DailyRecordActivity)
public class DailyRecordActivity extends AppCompatActivity {
    public static final String PARAM_NAME = "userId";

    @Autowired
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        ButterKnife.bind(this);
    }
}