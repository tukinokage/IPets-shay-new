package com.example.bbsmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bbsmodule.adapter.PostInfoListAdapter;
import com.example.bbsmodule.entity.BBSPost;
import com.example.bbsmodule.entity.params.CommitCommentParam;
import com.example.bbsmodule.entity.result.CommitCommentResult;
import com.example.bbsmodule.entity.result.GetPostCommentResult;
import com.example.bbsmodule.entity.result.GetPostInfoResult;
import com.example.bbsmodule.viewmodel.CommitCommentViewModel;
import com.example.bbsmodule.viewmodel.CommitCommentViewModelFactory;
import com.example.bbsmodule.viewmodel.PostInfoModelFactory;
import com.example.bbsmodule.viewmodel.PostInfoViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.GlideLoadEngine;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.adapter.SelectPicAdapter;
import com.shay.baselibrary.dto.Picture;
import com.shay.baselibrary.dto.Post;
import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.dto.result.UploadPicResult;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostInfoActivity extends AppCompatActivity {

    private PostInfoViewModel postInfoViewModel;
    private CommitCommentViewModel commitCommentViewModel;
    private SelectPicAdapter selectPicAdapter;
    private PostInfoListAdapter postInfoListAdapter;

    public static final int REQUEST_CODE_CHOOSE = 001;
    private final int MAX_PIC_NUM = 9;

    public static final String POST_DATA_BUNDLE_NAME = "BBSPost";
    public static final String SEARCH_DATA_BUNDLE_NAME = "searchCondition";
    private BBSPost currentBBPost;

    @BindView(R.id.comment_text_input_et)
    TextInputEditText commentTextInput;

    @BindView(R.id.comment_select_pic_gridview)
    GridView picGridView;

    @BindView(R.id.comment_text_confrim_btn)
    Button commitButton;

    @BindView(R.id.post_info_comment_pull_rbtn)
    QMUIRoundButton qmuiRoundButton;

    @BindView(R.id.comment_commit_area_layout)
    ConstraintLayout commitCommentLayout;

    @BindView(R.id.activity_post_top_back_btn)
    Button backButton;

    @BindView(R.id.activity_post_info_top_title)
    TextView titleTextView;

    @BindView(R.id.activity_post_info_top_title)
    RecyclerView contentRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        ButterKnife.bind(this);

        postInfoViewModel = new ViewModelProvider(this, new PostInfoModelFactory())
                .get(PostInfoViewModel.class);

        commitCommentViewModel = new ViewModelProvider(this, new CommitCommentViewModelFactory())
                .get(CommitCommentViewModel.class);

        currentBBPost = (BBSPost) getIntent().getExtras().get(POST_DATA_BUNDLE_NAME);

        init();
        initListener();
        initObserver();
    }

    private void init(){

        postInfoListAdapter = new PostInfoListAdapter(this);
        contentRV.setAdapter(postInfoListAdapter);

        commitButton.setEnabled(false);
        selectPicAdapter = new SelectPicAdapter(this);
        picGridView.setAdapter(selectPicAdapter);

        if(currentBBPost != null){
            postInfoViewModel.getPost(currentBBPost.getPostId());
            postInfoViewModel.getComment(currentBBPost.getPostId());
        }else {
            ToastUntil.showToast("错误操作", this);
        }

    }

    private void initListener(){

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //显示
        postInfoListAdapter.setOnClickListener(new PostInfoListAdapter.ClickUserHeadIconListener() {
            @Override
            public void onclick(Post post) {
                String id = post.getPostId();
                //
            }

            @Override
            public void onclick(int position) {
                //
            }
        });

        postInfoListAdapter.setClikPicListener(new PostInfoListAdapter.ClikPicListener() {
            @Override
            public void onClick(String picName) {
                //加载图片大图
            }
        });



        //发表回复
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    commitButton.setEnabled(false);
                }else {
                    commitButton.setEnabled(true);
                }
            }
        };

        commentTextInput.addTextChangedListener(textWatcher);

        qmuiRoundButton.setOnClickListener(v -> {

        });

        selectPicAdapter.setAddOnclickListener(v -> selectPic());

        selectPicAdapter.setCancelBtnListener(position -> commitCommentViewModel.removePic(position));

        selectPicAdapter.setItemOnclickListener(position -> {

        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCommentEditorStatus(false);
                commitCommentViewModel.sendComment(currentBBPost.getPostId(), commentTextInput.getText().toString());
                //不可编辑状态
            }
        });

    }

    private void initObserver(){

        postInfoViewModel.getGetPostInfoResultMutableLiveData().observe(this, new Observer<GetPostInfoResult>() {
            @Override
            public void onChanged(GetPostInfoResult getPostInfoResult) {
                if(TextUtils.isEmpty(getPostInfoResult.getErrorMsg())){
                    postInfoListAdapter.setPost(getPostInfoResult.getPost());
                    postInfoListAdapter.notifyDataSetChanged();
                }else {
                    ToastUntil.showToast(getPostInfoResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });

        postInfoViewModel.getGetPostCommentResultMutableLiveData().observe(this, new Observer<GetPostCommentResult>() {
            @Override
            public void onChanged(GetPostCommentResult getPostCommentResult) {
                if(TextUtils.isEmpty(getPostCommentResult.getErrorMsg())){
                    postInfoListAdapter.setCommentsList(getPostCommentResult.getCommentList());
                    postInfoListAdapter.notifyDataSetChanged();
                }else {
                    ToastUntil.showToast(getPostCommentResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });


        commitCommentViewModel.getCommitPicListMutableLiveData().observe(this, new Observer<List<PostPicInfo>>() {
            @Override
            public void onChanged(List<PostPicInfo> list) {
                updatePicList(list);
            }
        });

        commitCommentViewModel.getCommitCommentResultMutableLiveData().observe(this, new Observer<CommitCommentResult>() {
            @Override
            public void onChanged(CommitCommentResult commitCommentResult) {
                if(TextUtils.isEmpty(commitCommentResult.getErrorMsg())){
                    //可编辑状态
                    ToastUntil.showToast("回复成功", AppContext.getContext());
                    setCommentEditorStatus(true);

                }else {
                    ToastUntil.showToast(commitCommentResult.getErrorMsg(), AppContext.getContext());
                    setCommentEditorStatus(true);
                }
            }
        });

        commitCommentViewModel.getUploadPicResultMutableLiveData().observe(this, new Observer<UploadPicResult>() {
            @Override
            public void onChanged(UploadPicResult uploadPicResult) {
                if(TextUtils.isEmpty(uploadPicResult.getErrorMsg())){
                    commitCommentViewModel.isPicAllIsSucceed();
                    commitCommentViewModel.setPicSucceed(uploadPicResult.getIndex());
                }else {
                    setCommentEditorStatus(true);
                    commitCommentViewModel.setPicFailed(uploadPicResult.getIndex());
                }
            }
        });


    }

    //matisse选择图片
    private void selectPic(){
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.shay.ipets.fileprovider"))
                .countable(true)
                .maxSelectable(MAX_PIC_NUM - commitCommentViewModel.mContentListLength())
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return null;
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(item.getContentUri());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStream, null, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

                            /*if (width >= 500)
                                return new IncapableCause("宽度超过500px");*/

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        return null;

                    }
                })
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.bottom_bar_height))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideLoadEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void updatePicList(List list){
        selectPicAdapter.setPostPicInfoList(list);
        selectPicAdapter.notifyDataSetChanged();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            commitCommentViewModel.selectPic(mSelected);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

    private void setCommentEditorStatus(boolean b){
        commentTextInput.setEnabled(b);
        picGridView.setEnabled(b);
        commitButton.setEnabled(b);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commitCommentViewModel.cancelAsyncTask();
    }
}