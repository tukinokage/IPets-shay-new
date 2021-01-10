package com.shay.baselibrary.MyTimeUtil;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class TimeUntil {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDateTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = formatter.format(date);

        return time;
    }

    public  static String getCurrentSysDateTime(){
        SimpleDateFormat simpleDateFormat = null;// HH:mm:ss
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {//获取当前时间
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            return simpleDateFormat.format(date);
        }else {
            return "";
        }


    }

}
