package com.example.usermodule.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.usermodule.R;
import com.example.usermodule.R2;
import com.example.usermodule.entity.result.GetUserResult;
import com.example.usermodule.viewmodel.UserInfoModelFactory;
import com.example.usermodule.viewmodel.UserInfoViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.UserInfo;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.baselibrary.myexceptions.MyException;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.fragmentUrl.UserInfoFragment)
public class UserInfoFragment extends Fragment {

    public static final String PARAM_NAME = "userId";

    public String userId;

    UserInfoViewModel userInfoViewModel;

   boolean  isMyUserInfo = false;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoViewModel = new ViewModelProvider(this, new UserInfoModelFactory())
                .get(UserInfoViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_info, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        ButterKnife.bind(this, getActivity());
        ARouter.getInstance().inject(this);
        initListener();
        initObserver();
        backBtn.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("UserInfoFragment：",  "UserInfoFragment onResume");
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            init();
        }
    }

    public void init()  {

        try {
            if(UserInfoUtil.isLogin()){
                isLogined = true;

            }else {
                 //未登录
                isLogined = false;
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
                        userId = UserInfoUtil.getUserId();
                        isMyUserInfo = true;
                }else {
                    isMyUserInfo = false;
                }

            }

            //判断是否是自己的账户
            if(isMyUserInfo){
                //开启修改头像功能
                initUpdateHeadIcon();
            }else {
                hideUpdateFunction();
            }

            if(userId != null){
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
         userInfoViewModel.getGetUserResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GetUserResult>() {
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

        userNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogined ){
                    ARouter.getInstance().build(AroutePath.LoginActivity).navigation();
                }
            }
        });

        /*backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

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
                Intent intent = new Intent(getActivity(), postcard.getDestination());
                startActivityForResult(intent, AroutePath.requestCode.REQUEST_CODE_PHONE);
            }
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
                userId = null;
                UserInfoUtil.saveUserId("");
                UserInfoUtil.saveUserToken("");
                UserInfoUtil.cleanSharedPreference(AppContext.getContext());
                isMyUserInfo = false;
                isLogined = false;
                init();
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

        updateInfoLy.setVisibility(View.VISIBLE);
        pwLy.setVisibility(View.VISIBLE);
        loginoutLy.setVisibility(View.VISIBLE);
        petStarLy.setVisibility(View.VISIBLE);
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
                    .placeholder(AppContext.getContext().getDrawable(R.color.material_blue_200))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.color.color_white)
                    .error(R.color.btn_filled_blue_bg_normal)
                    .into(headIv);
        }

        if(!TextUtils.isEmpty(userInfo.getBgPicName())) {
            Glide.with(AppContext.getContext())
                    .load(UrlUtil.STATIC_RESOURCE.BG_PIC_URL + userInfo.getBgPicName())
                    .placeholder(AppContext.getContext().getDrawable(R.color.material_blue_200))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.ic_img_preview_default)
                    .into(bgImageView);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        userId = null;
        userInfoViewModel.cancelAsyncTask();
    }
}
