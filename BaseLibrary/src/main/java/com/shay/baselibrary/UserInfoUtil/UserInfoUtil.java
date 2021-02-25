package com.shay.baselibrary.UserInfoUtil;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.shay.baselibrary.SharedPreferencesUtil.SharedPrefenccesUtil;

import java.io.File;

public class UserInfoUtil {
    public final static class UserInfoParamsName{
        public final static String USER_INFO_FILE_NAME = "user_info";

        public final static String USER_NAME = "username";
        public final static String USER_PASSWORD = "password";
        public final static String USER_TOKEN = "token";
        public final static String USER_ID = "userId";
    }


    public static String getUserId() throws Exception{
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_ID);
    }

    public static String getUserName()throws Exception{
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_NAME);
    }

    public static String getUserToken()throws Exception{
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_TOKEN);
    }

    synchronized public static boolean saveUserId(String id){

        return SharedPrefenccesUtil.saveValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_ID,
                id);

    }
    synchronized public static boolean saveUserName(String name){
        return SharedPrefenccesUtil.saveValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_NAME,
                name);
    }

    synchronized public static boolean saveUserToken(String token){
        return SharedPrefenccesUtil.saveValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_TOKEN,
                token);
    }

    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    public static boolean isLogin() {
        try {
            if(TextUtils.isEmpty(getUserId()) || TextUtils.isEmpty(getUserToken())){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }



    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
