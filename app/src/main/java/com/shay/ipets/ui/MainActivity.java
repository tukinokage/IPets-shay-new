package com.shay.ipets.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.dto.result.ConfrimPhoneResult;
import com.shay.ipets.R;
import com.shay.ipets.ui.dialog.OperatorDialog;


import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = AroutePath.MainActivity)
public class MainActivity extends FragmentActivity {
    OperatorDialog dialog;
    @BindView(R.id.bottom_operate_btn)
    Button operaterButton;

    FragmentTransaction ft;
    Fragment bbsFragment;
    Fragment petsFragment;
    Fragment userFragment;

    @BindView(R.id.bottom_baike_btn)
    Button bakeBtn;
    @BindView(R.id.bottom_bbs_btn)
    Button bbsBtn;
    @BindView(R.id.bottom_my_info_btn)
    Button myInfoBtn;

    private Fragment mContent;
    FrameLayout contentLayout;

    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder =  ButterKnife.bind(this);

        ARouter.getInstance().inject(this);
        ft = getSupportFragmentManager().beginTransaction();
        contentLayout = findViewById(R.id.nav_host_fragment);
        initListener();
        initFramgmenTransaction();


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

    @Override
    protected void onResume() {
        super.onResume();
        //通知fragmen刷新
        ft.setMaxLifecycle(mContent, Lifecycle.State.RESUMED);
    }

    private void initListener(){

        bakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchContent(1);
            }
        });
        bbsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchContent(2);
            }
        });

      myInfoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              switchContent(3);
          }
      });

        operaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dialog == null){
                    dialog = new OperatorDialog(MainActivity.this);
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }else {

                    dialog.show();
                }

            }
        });
    }


    private void initFramgmenTransaction() {
        if (bbsFragment != null && bbsFragment.isAdded() ) {
            ft.remove(bbsFragment);
        }
        if (petsFragment != null && petsFragment.isAdded() ) {
            ft.remove(petsFragment);
        }
        if (userFragment != null && userFragment.isAdded() ) {
            ft.remove(userFragment);
        }
        ft.commitAllowingStateLoss();
        bbsFragment = null;
        petsFragment = null;
        userFragment = null;

        switchContent(1);

    }

    private void switchContent(int itemNum) {

        Fragment mfragment = null;
        ft = getSupportFragmentManager().beginTransaction();

        if(itemNum == 1) {
            if(petsFragment == null) {
                petsFragment = (Fragment) ARouter.getInstance().build(AroutePath.fragmentUrl.PetsContentFragment).navigation();;
            }
            mfragment = petsFragment;
        }

        else if(itemNum == 2) {
            if(bbsFragment == null) {
                bbsFragment = (Fragment) ARouter.getInstance().build(AroutePath.fragmentUrl.BBSMainFragment).navigation();;

            }
            mfragment = bbsFragment;
        }

        else if(itemNum == 3) {
            if(userFragment == null) {
                userFragment = (Fragment) ARouter.getInstance().build(AroutePath.fragmentUrl.UserInfoFragment).navigation();;

            }
            mfragment = userFragment;
        }

        if(mContent == null){
            ft.add(contentLayout.getId(), mfragment).commit();
            mContent = mfragment;
        }else {
            if (!mfragment.isAdded()) {
                ft.hide(mContent).add(contentLayout.getId(), mfragment).commitAllowingStateLoss();
            } else {
                ft.hide(mContent).show(mfragment).commitAllowingStateLoss();
                ft.setMaxLifecycle(mfragment, Lifecycle.State.RESUMED);
            }
            mContent = mfragment;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AroutePath.resultCode.PHONE_RESULT_CODE){
            ConfrimPhoneResult confrimPhoneResult = (ConfrimPhoneResult) data.getExtras().get(AroutePath.paramName.RESULT_PARAM_NAME);
            Postcard postcard = ARouter.getInstance().build(AroutePath.SetPassActivity);
            LogisticsCenter.completion(postcard);
            Intent intent = new Intent(this, postcard.getDestination());
            intent.putExtra(AroutePath.paramName.PHONE_TOKEN_PARAM_NAME, confrimPhoneResult.getPhoneToken());
            startActivityForResult(intent, AroutePath.requestCode.REQUEST_CODE_SET_PWD);

        }else if( resultCode == AroutePath.resultCode.SET_PW_RESULT_CODE){
            boolean result = (boolean) data.getExtras().get(AroutePath.paramName.SET_PW_RESULT_PARAM_NAME);
            if(result){
                ToastUntil.showToast("成功更改", AppContext.getContext());
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
