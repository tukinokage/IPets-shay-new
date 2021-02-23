package com.example.bbsmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bbsmodule.adapter.PostInfoListAdapter;
import com.example.bbsmodule.entity.BBSPost;
import com.example.bbsmodule.entity.result.CommitCommentResult;
import com.example.bbsmodule.entity.result.GetPostCommentResult;
import com.example.bbsmodule.entity.result.GetPostInfoResult;
import com.example.bbsmodule.viewmodel.CommitCommentViewModel;
import com.example.bbsmodule.viewmodel.CommitCommentViewModelFactory;
import com.example.bbsmodule.viewmodel.PostInfoModelFactory;
import com.example.bbsmodule.viewmodel.PostInfoViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.GlideLoadEngine;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.adapter.SelectPicAdapter;
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

@Route(path = AroutePath.PostInfoActivity)
public class PostInfoActivity extends AppCompatActivity {

    private PostInfoViewModel postInfoViewModel;
    private CommitCommentViewModel commitCommentViewModel;
    private SelectPicAdapter selectPicAdapter;
    private PostInfoListAdapter postInfoListAdapter;

    public static final int REQUEST_CODE_CHOOSE = 001;
    private final int MAX_PIC_NUM = 9;
    int mHiddenViewMeasuredHeight;

    public static final String POST_DATA_BUNDLE_NAME = "BBSPost";
    @Autowired
    public String postId;
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

    @BindView(R.id.post_info_comment_rv)
    RecyclerView contentPostItemRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        postInfoViewModel = new ViewModelProvider(this, new PostInfoModelFactory())
                .get(PostInfoViewModel.class);
        commitCommentViewModel = new ViewModelProvider(this, new CommitCommentViewModelFactory())
                .get(CommitCommentViewModel.class);

        if(getIntent().getExtras().isEmpty()){
            currentBBPost = new BBSPost();
            currentBBPost.setPostId(postId);
        }else {
            currentBBPost = (BBSPost) getIntent().getExtras().get(POST_DATA_BUNDLE_NAME);
        }


        init();
        initListener();
        initObserver();
    }

    private void init(){

       //float mDensity = getResources().getDisplayMetrics().density;
        commitCommentLayout.measure(0,0);
        //mHiddenViewMeasuredHeight = (int) (mDensity * commitCommentLayout.getHeight() + 0.5);
       //输入框高度
        //mHiddenViewMeasuredHeight = commitCommentLayout.getHeight() + 10;
        mHiddenViewMeasuredHeight = 865;

        postInfoListAdapter = new PostInfoListAdapter(this);
        contentPostItemRV.setLayoutManager(new LinearLayoutManager(this));
        contentPostItemRV.setAdapter(postInfoListAdapter);

        commitButton.setEnabled(false);
        selectPicAdapter = new SelectPicAdapter(this);
        selectPicAdapter.setPostPicInfoList(new ArrayList<>());
        picGridView.setAdapter(selectPicAdapter);



        if(currentBBPost != null){
            postInfoViewModel.getPost(currentBBPost.getPostId());
            postInfoViewModel.getComment(currentBBPost.getPostId());
        }else {
            ToastUntil.showToast("错误操作", this);
        }

    }


    private void animatorOpen(View v){
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animatorClose(View v){
        int origHeight = v.getHeight();
        ValueAnimator animator = createDropAnimator(v, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);

            }
        });
        return animator;
    }

    private void initListener(){

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //显示
        postInfoListAdapter.setOnUserHeadClickListener(new PostInfoListAdapter.ClickUserHeadIconListener() {
            @Override
            public void onclick(Post post) {
                //帖子头像点击
                String userId = post.getUserId();
                ARouter.getInstance().build(AroutePath.UserInfoActivity)
                        .withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId)
                         .navigation();
            }

            @Override
            public void onclick(int position) {
                //回复头像点击
                String userId = postInfoListAdapter.getCommentsList().get(position).getUserId();
                ARouter.getInstance().build(AroutePath.UserInfoActivity)
                        .withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId)
                        .navigation();
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

        //动画
        qmuiRoundButton.setOnClickListener(v -> {

            if(commitCommentLayout.getVisibility()  == View.GONE){
                animatorOpen(commitCommentLayout);
            }else {
                animatorClose(commitCommentLayout);
            }
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
                    Post post = getPostInfoResult.getPost();
                    postInfoListAdapter.setPost(post);
                    titleTextView.setText(getPostInfoResult.getPost().getTitle());
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
                    postInfoViewModel.getComment(currentBBPost.getPostId());
                    animatorClose(commitCommentLayout);

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
                    commitCommentViewModel.setPicSucceed(uploadPicResult.getIndex());
                    commitCommentViewModel.isPicAllIsSucceed();

                }else {
                    setCommentEditorStatus(true);
                    commitCommentViewModel.setPicFailed(uploadPicResult.getIndex());
                }
            }
        });


    }

    //matisse选择图片
    private void selectPic(){
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_CHOOSE);
            return;
        }

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