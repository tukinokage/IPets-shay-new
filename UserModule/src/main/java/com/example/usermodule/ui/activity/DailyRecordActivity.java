package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.example.usermodule.viewmodel.GetDailyRecordListViewModel;
import com.example.usermodule.viewmodel.GetDailyViewModelFactory;
import com.example.usermodule.viewmodel.GetUserCommentListViewModel;
import com.example.usermodule.viewmodel.GetUserCommentViewModelFactory;
import com.shay.baselibrary.AroutePath;

import butterknife.ButterKnife;

@Route(path = AroutePath.DailyRecordActivity)
public class DailyRecordActivity extends AppCompatActivity {
    public static final String PARAM_NAME = "userId";

    GetDailyRecordListViewModel dailyRecordListViewModel;
    @Autowired
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        ButterKnife.bind(this);
        dailyRecordListViewModel = new ViewModelProvider(this, new GetDailyViewModelFactory())
                .get(GetDailyRecordListViewModel.class);

        init();
        initListener();
        initObserver();
    }

    private void init(){

    }

    private void initObserver(){

    }

    private void initListener(){

    }
}
