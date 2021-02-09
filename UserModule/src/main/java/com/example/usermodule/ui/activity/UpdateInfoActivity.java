package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.usermodule.R;
import com.example.usermodule.entity.result.UpdateUserInfoResult;
import com.example.usermodule.viewmodel.UpdateInfoViewModel;
import com.example.usermodule.viewmodel.UpdateUserInfoModelFactory;
import com.example.usermodule.viewmodel.UserInfoModelFactory;
import com.example.usermodule.viewmodel.UserInfoViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.UserInfo;
import com.shay.baselibrary.UserInfoUtil.*;
import com.wildma.pictureselector.PictureSelector;



import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.UpdateInfoActivity)
public class UpdateInfoActivity extends AppCompatActivity {

    @BindView(R.id.update_info_head_iv)
    QMUIRadiusImageView headIv;
    @BindView(R.id.update_info_bg_iv)
    ImageView imageView;
    @BindView(R.id.update_info_nickname_input_text)
    TextInputEditText nickNameEditText;
    @BindView(R.id.update_info_sign_iv)
    TextInputEditText signTextInput;
    @BindView(R.id.update_info_sumit)
    TextView submitTv;
    @BindView(R.id.user_info_top_back_tv)
    TextView backTv;

    UserInfoViewModel userInfoViewModel;
    UpdateInfoViewModel updateInfoViewModel;
    UserInfo currentUserInfo;

    public static final int SELECT_HEAD_REQUEST_CODE = 0x01;
    public static final int SELECT_BG_REQUEST_CODE = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        ButterKnife.bind(this);

        userInfoViewModel = new ViewModelProvider(this, new UserInfoModelFactory())
                .get(UserInfoViewModel.class);
        updateInfoViewModel = new ViewModelProvider(this, new UpdateUserInfoModelFactory())
                .get(UpdateInfoViewModel.class);

        init();
        initListener();
        initObserver();
    }

    private void init() {
        try {
            //初始化个人信息
            userInfoViewModel.getMyInfo(UserInfoUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            ToastUntil.showToast("获取个人信息出错", AppContext.getContext());
        }
    }

    private void initListener(){

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headIv.setOnClickListener(v -> {
           //  PictureSelector.create(UpdateInfoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
             PictureSelector.create(UpdateInfoActivity.this, SELECT_HEAD_REQUEST_CODE)
                    .selectPicture(true, 200, 200, 1, 1);
        });

        imageView.setOnClickListener(v -> PictureSelector.create(UpdateInfoActivity.this, SELECT_BG_REQUEST_CODE)
                .selectPicture(  false));

        nickNameEditText.setOnClickListener(v -> {

        });

        signTextInput.setOnClickListener(v -> {

        });

        submitTv.setOnClickListener(v -> {
            String name = "";
            String sign = "";
            if(!nickNameEditText.getText().toString().isEmpty()){
                name = nickNameEditText.getText().toString().trim();
            }

            if(!signTextInput.getText().toString().isEmpty()){
                sign = nickNameEditText.getText().toString().trim();
            }

            updateInfoViewModel.updateInfo(name, sign);

        });
    }

    private void  initObserver(){
        userInfoViewModel.getGetUserResultMutableLiveData().observe(this, getUserResult -> {
            if(TextUtils.isEmpty(getUserResult.getErrorMsg())){
                UserInfo userInfo = getUserResult.getUserInfo();
                signTextInput.setText(userInfo.getUserSign());
                nickNameEditText.setText(userInfo.getUserName());
                currentUserInfo = userInfo;
                loadPic(userInfo);
            }else {
                ToastUntil.showToast(getUserResult.getErrorMsg(), AppContext.getContext());
            }
        });

        updateInfoViewModel.getUpdateBgResultMutableLiveData().observe(this, new Observer<UpdateUserInfoResult>() {
            @Override
            public void onChanged(UpdateUserInfoResult updateUserInfoResult) {
                if(TextUtils.isEmpty(updateUserInfoResult.getErrorMsg())){
                    ToastUntil.showToast("上传完毕" , AppContext.getContext());
                    if( currentUserInfo != null){
                        loadPic(currentUserInfo);
                    }
                }else {
                    ToastUntil.showToast(updateUserInfoResult.getErrorMsg() , AppContext.getContext());
                }
            }
        });

        updateInfoViewModel.getUpdateHeadResultMutableLiveData().observe(this, new Observer<UpdateUserInfoResult>() {
            @Override
            public void onChanged(UpdateUserInfoResult updateUserInfoResult) {
                if(TextUtils.isEmpty(updateUserInfoResult.getErrorMsg())){
                    ToastUntil.showToast("上传完毕" , AppContext.getContext());
                    if( currentUserInfo != null){
                        loadPic(currentUserInfo);
                    }
                }else {
                    ToastUntil.showToast(updateUserInfoResult.getErrorMsg() , AppContext.getContext());
                }
            }
        });

        updateInfoViewModel.getUpdateUserInfoResultMutableLiveData().observe(this, new Observer<UpdateUserInfoResult>() {
            @Override
            public void onChanged(UpdateUserInfoResult updateUserInfoResult) {
                if(TextUtils.isEmpty(updateUserInfoResult.getErrorMsg())){
                    ToastUntil.showToast("修改完成" , AppContext.getContext());
                }else {
                    ToastUntil.showToast(updateUserInfoResult.getErrorMsg() , AppContext.getContext());
                }
            }
        });



    }

    private void loadPic(UserInfo userInfo){

        if(!TextUtils.isEmpty(userInfo.getHeadPicName())){
            Glide.with(AppContext.getContext())
                    .load(UrlUtil.STATIC_RESOURCE.HEAD_ICON_URL)
                    .placeholder(R.drawable.head_icon)
                    .error(R.drawable.ic_img_preview_default)
                    .into(headIv);
        }

        if(!TextUtils.isEmpty(userInfo.getBgPicName())) {
            Glide.with(AppContext.getContext())
                    .load(UrlUtil.STATIC_RESOURCE.BG_PIC_URL)
                    .placeholder(R.drawable.ic_img_preview_default)
                    .into(imageView);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == SELECT_HEAD_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_RESULT);
                updateInfoViewModel.uploadHeadImg(picturePath);
            }
        }else  if(requestCode == SELECT_BG_REQUEST_CODE){
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_RESULT);
                updateInfoViewModel.uploadBg(picturePath);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateInfoViewModel.cancelAsyncTask();
    }
}
