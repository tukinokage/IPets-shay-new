package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.example.usermodule.viewmodel.GetUserCommentListViewModel;
import com.example.usermodule.viewmodel.GetUserCommentViewModelFactory;
import com.shay.baselibrary.AroutePath;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.CommentActivity)
public class CommentListActivity extends AppCompatActivity {
    public static final String PARAM_NAME = "userId";

    @Autowired
    private String userId;

    GetUserCommentListViewModel commentListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);

        commentListViewModel = new ViewModelProvider(this, new GetUserCommentViewModelFactory())
                .get(GetUserCommentListViewModel.class);

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
