package com.example.petsandinfo.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AdvanceSwipeRefreshLayout extends SwipeRefreshLayout {

    private View view;
    InterceptEventConditionListener interceptEventConditionListener;
    public AdvanceSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public AdvanceSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(view != null){
            //根据监听器
            return interceptEventConditionListener.isInterceptTouchEvent();
        }

        return super.onInterceptHoverEvent(ev);
    }

    public void setInterceptEventConditionListener(InterceptEventConditionListener interceptEventConditionListener){

        this.interceptEventConditionListener = interceptEventConditionListener;
    }

    public interface InterceptEventConditionListener{
        //返回true拦截，false不拦截
        boolean isInterceptTouchEvent();
    }
}
