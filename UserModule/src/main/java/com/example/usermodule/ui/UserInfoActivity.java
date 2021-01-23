package com.example.usermodule.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.bumptech.glide.Glide;
import com.example.usermodule.R;
import com.example.usermodule.entity.UserInfo;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.viewmodel.UserInfoModelFactory;
import com.example.usermodule.viewmodel.UserInfoViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.NetUtil.HttpUtil;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.myexceptions.MyException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity {
    UserInfoViewModel userInfoViewModel;

    @Autowired
    boolean isMyUserInfo = true;
    @Autowired
    String userId;

    @BindView(R.id.user_info_top_back_tv)
    TextView backBtn;
    @BindView(R.id.user_info_bg_pic_iv)
    ImageView bgImageView;
    @BindView(R.id.user_info_head_qriv)
    QMUIRadiusImageView headIv;
    @BindView(R.id.user_info_sign)
    TextView signTv;
    @BindView(R.id.user_info_name_textview)
    TextView userNameTv;
    @BindView(R.id.user_info_post_ly)
    LinearLayout postLy;
    @BindView(R.id.user_info_post_num_tv)
    TextView postNumTv;
    @BindView(R.id.user_info_daliyrecord_ly)
    LinearLayout dailyRecordLy;
    @BindView(R.id.user_info_daliyrecord_num_tv)
    TextView daliyrecordNumTv;
    @BindView(R.id.user_info_comment_ly)
    LinearLayout commentLy;
    @BindView(R.id.user_info_updateinfo_ly)
    LinearLayout updateInfoLy;
    @BindView(R.id.user_info_alertpw_ly)
    LinearLayout pwLy;
    @BindView(R.id.user_info_loginout_ly)
    LinearLayout loginoutLy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        userInfoViewModel = new ViewModelProvider(this, new UserInfoModelFactory())
                .get(UserInfoViewModel.class);

        init();
        initListener();
        initObserver();
    }

    public void init(){
        //判断是否是自己的账户
        if(isMyUserInfo){
            //开启修改头像功能
            initUpdateHeadIcon();
            try {
                userInfoViewModel.getMyInfo();
            } catch (MyException e){
                ToastUntil.showToast(e.getMessage(), AppContext.getContext());
            } catch (Exception e) {
                e.printStackTrace();
                ToastUntil.showToast("应用出错", AppContext.getContext());
            }
        }else {
            hideUpdateFunction();
            userInfoViewModel.getUserInfo(userId);
        }
    }

    private void initObserver(){
         userInfoViewModel.getGetUserResultMutableLiveData().observe(this, new Observer<GetUserResult>() {
             @Override
             public void onChanged(GetUserResult getUserResult) {
                 if(TextUtils.isEmpty(getUserResult.getErrorMsg())){
                     UserInfo userInfo = getUserResult.getUserInfo();
                     signTv.setText(userInfo.getUserSign());
                     userNameTv.setText(userInfo.getUserName());
                     postNumTv.setText(userInfo.getPostNum());
                     daliyrecordNumTv.setText(userInfo.getDailyNum());
                     loadPic(userInfo);
                 }else {
                     ToastUntil.showToast(getUserResult.getErrorMsg(), AppContext.getContext());
                 }
             }
         });
    }

    private void initListener(){

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //跳转帖子列表
        postLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //跳转日志列表
        dailyRecordLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //跳转回复列表
        commentLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //跳转修改信息
        updateInfoLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //pw
        pwLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //loginout
        loginoutLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //

    }

    public void initUpdateHeadIcon(){
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void hideUpdateFunction(){
        updateInfoLy.setVisibility(View.INVISIBLE);
        pwLy.setVisibility(View.INVISIBLE);
        loginoutLy.setVisibility(View.INVISIBLE);
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
                    .into(bgImageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoViewModel.cancelAsyncTask();
    }
}
