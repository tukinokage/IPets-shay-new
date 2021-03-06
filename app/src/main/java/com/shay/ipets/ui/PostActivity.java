package com.shay.ipets.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.GlideLoadEngine;
import com.shay.baselibrary.enums.PostTypeEnum;
import com.shay.ipets.R;
import com.shay.ipets.adapter.SelectPicAdapter;
import com.shay.ipets.entity.result.PostResult;
import com.shay.baselibrary.dto.result.UploadPicResult;
import com.shay.ipets.viewmodel.PostViewModel;
import com.shay.ipets.viewmodel.PostViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.main_activity_go_register_tv)
    public TextView backTv;
    @BindView(R.id.post_select_pic_gridview)
    public GridView selectGridView;
    @BindView(R.id.post_text_confrim_btn)
    public Button submitButton;
    @BindView(R.id.post_text_input_et)
    public TextInputEditText contentTextInput;
    @BindView(R.id.post_title_input_et)

    public TextInputEditText titleTextInput;
    @BindView(R.id.post_post_class_tv)
    public TextView classSelectTv;

    private SelectPicAdapter selectPicAdapter;

    private PostViewModel postViewModel;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final int REQUEST_CODE_CHOOSE = 001;
    private final int MAX_PIC_NUM = 9;

    private int currentClass = 1;
    private String[] classPositives = new String[PostTypeEnum.values().length - 1];

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        postViewModel = new ViewModelProvider(this, new PostViewModelFactory()).get(PostViewModel.class);

        selectPicAdapter = new SelectPicAdapter(this);
        selectPicAdapter.setPostPicInfoList(postViewModel.getSelectPicList());
        selectGridView.setAdapter(selectPicAdapter);
        init();
        initListener();
        initObserver();
    }

    private void init(){
       for(int i = 1; i < PostTypeEnum.values().length; i++){
           classPositives[i-1] = PostTypeEnum.getRankEnumByNum(i).getRankName();
       }


    }
    private void initObserver(){
        postViewModel.getPostPicInfoMutableLiveData().observe(this, new Observer<List<PostPicInfo>>() {
            @Override
            public void onChanged(List<PostPicInfo> list) {
                updatePicList(list);
            }
        });

        postViewModel.getPostResultMutableLiveData().observe(this, new Observer<PostResult>() {
            @Override
            public void onChanged(PostResult postResult) {
                postViewModel.clearSucceed();

                if(TextUtils.isEmpty(postResult.getErrorMsg())){
                    ToastUntil.showToast("发送成功", AppContext.getContext());
                    finish();
                } else {
                    ToastUntil.showToast(postResult.getErrorMsg(), AppContext.getContext());

                }
            }
        });

        //single pic upload 
        postViewModel.getUploadPicResultMutableLiveData().observe(this, new Observer<UploadPicResult>() {
            @Override
            public void onChanged(UploadPicResult uploadPicResult) {
               if(TextUtils.isEmpty(uploadPicResult.getErrorMsg())){
                   postViewModel.setPicSucceed(uploadPicResult.getIndex());
                   //检测是否全部上传
                   postViewModel.isPicAllIsSucceed();

               } else {
                   ToastUntil.showToast(uploadPicResult.getErrorMsg(), AppContext.getContext());
                   postViewModel.setPicFailed(uploadPicResult.getIndex());
               }
            }
        });

    }

    private void initListener(){


        classSelectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                builder.setTitle("选择帖子的类型")
                        .setItems(classPositives, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PostActivity.this.currentClass = which + 1;
                                PostActivity.this.classSelectTv.setText("当前类型：" + PostTypeEnum.getRankEnumByNum(which + 1).getRankName());
                            }
                        });

                builder.show();
            }
        });


        selectPicAdapter.setAddOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });

        selectPicAdapter.setCancelBtnListener(new SelectPicAdapter.CancelBtnListener() {
            @Override
            public void onclick(int position) {
                postViewModel.removePic(position);
            }
        });

        //re upload
        selectPicAdapter.setItemOnclickListener(new SelectPicAdapter.ItemOnclickListener() {
            @Override
            public void onclick(int position) {
                postViewModel.uploadPic(position);
            }
        });

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先上传图片

                String title = titleTextInput.getText().toString();
                String contentText = contentTextInput.getText().toString();
                int type = currentClass;

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(contentText)){
                    ToastUntil.showToast("请输入正确内容", AppContext.getContext());
                    return;
                }
                postViewModel.submitAll(title, contentText, type);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        postViewModel.cancelAsyncTask();
    }

    private void updatePicList(List list){
        selectPicAdapter.setPostPicInfoList(list);
        selectPicAdapter.notifyDataSetChanged();
    }

    private void selectPic(){

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_CHOOSE);
            return;
        }

       if(postViewModel.mContentListLength() == MAX_PIC_NUM){
           ToastUntil.showToast("不能超过" + MAX_PIC_NUM + "张", AppContext.getContext());
       }

        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.shay.ipets.fileprovider"))
                .countable(true)
                .maxSelectable(MAX_PIC_NUM - postViewModel.mContentListLength())
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

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            postViewModel.selectPic(mSelected);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}
