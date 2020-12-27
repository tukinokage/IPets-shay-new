package com.shay.baselibrary.UserInfoUtil;

import com.shay.baselibrary.SharedPreferencesUtil.SharedPrefenccesUtil;

public class UserInfoUtil {
    public final static class UserInfoParamsName{
        public final static String USER_INFO_FILE_NAME = "user_info";

        public final static String USER_NAME = "username";
        public final static String USER_PASSWORD = "password";
        public final static String USER_TOKEN = "token";
        public final static String USER_ID = "userId";
    }


    public static String getUserId(){
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_ID);
    }

    public static String getUserName(){
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_NAME);
    }

    public static String getUserToken(){
        return SharedPrefenccesUtil.getValue(
                UserInfoParamsName.USER_INFO_FILE_NAME,
                UserInfoParamsName.USER_TOKEN);
    }

}
