package com.shay.baselibrary.UrlInfoUtil;

public class UrlUtil {
    public static class BASE_URL{
        public static final String BASE_URL = "http://172.27.35.1:7371/";
        public static final String ALI_MSG_API_BASE_URL = "http://dysmsapi.aliyuncs.com/?";
    }

    public static class ALI_API_URL{
        public static final String SEND_PHONE_MSG = "http://dysmsapi.aliyuncs.com/?";
    }

    public static class PET_PIC_URL{

        public static final String HEAD_ICON_URL = BASE_URL.BASE_URL + "pet/pic/";
        public static final String DOWNLOAD_URL = BASE_URL.BASE_URL + "pic/download/";
    }

    public static class USER_URL{

        public static final String LOGIN_URL = BASE_URL.BASE_URL + "usr/login/";
        public static final String TOKEN_LOGIN_URL = BASE_URL.BASE_URL + "usr/loginToken/";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";

    }

    public static class PET_URL{

        public static final String GET_PET_LIST_URL = BASE_URL.BASE_URL + "pet/petlist/";
        public static final String TOKEN_LOGIN_URL = BASE_URL.BASE_URL + "pet/loginToken/";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";


    }

}
