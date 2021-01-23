package com.shay.baselibrary;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class AppContext extends Application {
    private static Context context;

    private  boolean isDebugArRoute = true;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

        if (isDebugArRoute){
            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(AppContext.this);

    }

    public static Context getContext(){

        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }


}
