package com.example.bbsmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.bbsmodule.viewmodel.PostInfoModelFactory;
import com.example.bbsmodule.viewmodel.PostInfoViewModel;

import butterknife.ButterKnife;

public class PostInfoActivity extends AppCompatActivity {

    PostInfoViewModel postInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        ButterKnife.bind(this);

        postInfoViewModel = new ViewModelProvider(this, new PostInfoModelFactory())
                .get(PostInfoViewModel.class);


    }


}
