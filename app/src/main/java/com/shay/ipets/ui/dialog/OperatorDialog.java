package com.shay.ipets.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.shay.ipets.R;
import com.shay.ipets.ui.DailyRecordActivity;
import com.shay.ipets.ui.MainActivity;
import com.shay.ipets.ui.PostActivity;
import com.shay.baselibrary.UserInfoUtil.*;
import com.shay.baselibrary.*;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatorDialog extends Dialog {
    @BindView(R.id.operator_dialog_daily_btn)
    Button dailyButton;
    @BindView(R.id.operator_dialog_post_btn)
    Button postButton;

    private Context context;
    public OperatorDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    protected OperatorDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected OperatorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.operator_menu_dialog_layout, null, false);
        addContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserInfoUtil.isLogin()){
                    ToastUntil.showToast("请先登录", context);
                    return;
                }
                Intent intent = new Intent(getContext(), PostActivity.class);
                getContext().startActivity(intent);
            }
        });
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserInfoUtil.isLogin()){
                    ToastUntil.showToast("请先登录", context);
                    return;
                }

                Intent intent = new Intent(getContext(), DailyRecordActivity.class);
                getContext().startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
       // layoutParams.gravity = Gravity.CENTER;
        /*layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;*/
        //layoutParams.gravity = Gravity.CENTER;
        //layoutParams.format = PixelFormat.TRANSLUCENT;

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setAttributes(layoutParams);
    }



}
