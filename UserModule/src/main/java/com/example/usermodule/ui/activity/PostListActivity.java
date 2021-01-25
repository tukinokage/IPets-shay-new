package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.shay.baselibrary.AroutePath;

import butterknife.ButterKnife;

@Route(path = AroutePath.PostListActivity)
public class PostListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        ButterKnife.bind(this);
    }
}
