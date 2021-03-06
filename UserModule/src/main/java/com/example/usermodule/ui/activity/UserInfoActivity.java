package com.example.usermodule.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.usermodule.R;
import com.example.usermodule.R2;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.dto.UserInfo;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.viewmodel.UserInfoModelFactory;
import com.example.usermodule.viewmodel.UserInfoViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.baselibrary.myexceptions.MyException;
import com.shay.baselibrary.UserInfoUtil.*;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.UserInfoActivity)
public class UserInfoActivity extends AppCompatActivity {

    public static final String PARAM_NAME = "userId";
    @Autowired
    public String userId;

    UserInfoViewModel userInfoViewModel;

    boolean isMyUserInfo = false;
    boolean isLogined = false;

    @BindView(R2.id.user_info_top_back_tv)
    TextView backBtn;
    @BindView(R2.id.user_info_bg_pic_iv)
    ImageView bgImageView;
    @BindView(R2.id.user_info_head_qriv)
    QMUIRadiusImageView headIv;
    @BindView(R2.id.user_info_sign)
    TextView signTv;
    @BindView(R2.id.user_info_name_textview)
    TextView userNameTv;
    @BindView(R2.id.user_info_post_ly)
    LinearLayout postLy;
    @BindView(R2.id.user_info_post_num_tv)
    TextView postNumTv;
    @BindView(R2.id.user_info_daliyrecord_ly)
    LinearLayout dailyRecordLy;
    @BindView(R2.id.user_info_daliyrecord_num_tv)
    TextView daliyrecordNumTv;
    @BindView(R2.id.user_info_comment_ly)
    LinearLayout commentLy;
    @BindView(R2.id.user_info_updateinfo_ly)
    LinearLayout updateInfoLy;
    @BindView(R2.id.user_info_alertpw_ly)
    LinearLayout pwLy;
    @BindView(R2.id.user_info_loginout_ly)
    LinearLayout loginoutLy;
    @BindView(R2.id.user_info_start_ly)
    LinearLayout petStarLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        userInfoViewModel = new ViewModelProvider(this, new UserInfoModelFactory())
                .get(UserInfoViewModel.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        initListener();
        initObserver();


        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void init()  {
        try {

            if(TextUtils.isEmpty(userInfoViewModel.getMyId())){
                //未登录
                isLogined = false;
            }else {
                isLogined = true;
            }


            if(userId == null){
                if(isLogined){
                    userId = userInfoViewModel.getMyId();
                    isMyUserInfo = true;
                }else {
                    isMyUserInfo = false;
                    userNameTv.setText("未登录(点击登录)");
                    signTv.setText("签名");
                }
            }else {
                if(isLogined){
                    if(userInfoViewModel.getMyId().equals(userId)){
                        isMyUserInfo = true;
                    }else {
                        isMyUserInfo = false;
                    }
                }

            }

            //判断是否是自己的账户
            if(isMyUserInfo){
                isLogined = true;
                //开启修改头像功能
                initUpdateHeadIcon();
                userInfoViewModel.getMyInfo(userId);

            }else {
                hideUpdateFunction();
                userInfoViewModel.getUserInfo(userId);
            }

        } catch (MyException e){
            ToastUntil.showToast(e.getMessage(), AppContext.getContext());
        } catch (Exception e) {
            e.printStackTrace();
            ToastUntil.showToast("应用出错", AppContext.getContext());
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
                     userId = userInfo.getUserId();
                     loadPic(userInfo);
                 }else {
                     ToastUntil.showToast(getUserResult.getErrorMsg(), AppContext.getContext());
                 }
             }
         });
    }

    private void initListener(){

        userNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogined ){
                    ARouter.getInstance().build(AroutePath.LoginActivity).navigation();
                }
            }
        });

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
                ARouter.getInstance().build(AroutePath.SearchResultActivity).withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId).navigation();
            }
        });

        //跳转日志列表
        dailyRecordLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(AroutePath.DailyRecordActivity).withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId).navigation();
            }
        });

        //跳转回复列表
        commentLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(AroutePath.CommentActivity).withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId).navigation();
            }
        });

        //跳转修改信息
        updateInfoLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                ARouter.getInstance().build(AroutePath.UpdateInfoActivity).withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId).navigation();
            }
        });

        //pw
        pwLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postcard postcard = ARouter.getInstance().build(AroutePath.PhoneActivity);
                LogisticsCenter.completion(postcard);
                Intent intent = new Intent(UserInfoActivity.this, postcard.getDestination());
                startActivityForResult(intent, AroutePath.requestCode.REQUEST_CODE_PHONE);    }
        });

        //跳转收藏宠物
        petStarLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(AroutePath.PetStarActivity).withString(AroutePath.paramName.USER_ID_PARAM_NAME, userId).navigation();
            }
        });


        //loginout
        loginoutLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoUtil.cleanSharedPreference(AppContext.getContext());
                ARouter.getInstance().build(AroutePath.LoginActivity).navigation();
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
        petStarLy.setVisibility(View.INVISIBLE);
    }


    private void loadPic(UserInfo userInfo){

        if(!TextUtils.isEmpty(userInfo.getHeadPicName())){
            Glide.with(AppContext.getContext())
                    .load(UrlUtil.STATIC_RESOURCE.HEAD_ICON_URL + userInfo.getHeadPicName())
                    .placeholder(R.drawable.head_icon)
                    .error(R.drawable.ic_img_preview_default)
                    .into(headIv);
        }

        if(!TextUtils.isEmpty(userInfo.getBgPicName())) {
            Glide.with(AppContext.getContext())
                    .load(UrlUtil.STATIC_RESOURCE.BG_PIC_URL + userInfo.getBgPicName())
                    .placeholder(R.drawable.ic_img_preview_default)
                    .into(bgImageView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AroutePath.requestCode.REQUEST_CODE_PHONE && resultCode == AroutePath.resultCode.PHONE_RESULT_CODE){
            ConfrimPhoneResult confrimPhoneResult = (ConfrimPhoneResult) data.getExtras().get(AroutePath.paramName.RESULT_PARAM_NAME);
            Postcard postcard = ARouter.getInstance().build(AroutePath.SetPassActivity);
            LogisticsCenter.completion(postcard);
            Intent intent = new Intent(this, postcard.getDestination());
            startActivityForResult(intent, AroutePath.requestCode.REQUEST_CODE_SET_PWD);

        }else if(requestCode == AroutePath.requestCode.REQUEST_CODE_SET_PWD && resultCode == AroutePath.resultCode.SET_PW_RESULT_CODE){
            boolean result = (boolean) data.getExtras().get(AroutePath.paramName.SET_PW_RESULT_PARAM_NAME);
            if(result){
                ToastUntil.showToast("成功更改", AppContext.getContext());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoViewModel.cancelAsyncTask();
    }
}
