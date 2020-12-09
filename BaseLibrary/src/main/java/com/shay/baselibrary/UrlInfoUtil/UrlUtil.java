package com.shay.baselibrary.UrlInfoUtil;

public class UrlUtil {
    public static class BASE_URL{
        public static final String BASE_URL = "http://172.27.35.1:7371/";
        public static final String ALI_MSG_API_BASE_URL = "http://dysmsapi.aliyuncs.com/?";
    }

    public static class PIC_URL{

        public static final String UPLOAD_URL = "http://172.27.35.1:7371/pic/upload";
        public static final String DOWNLOAD_URL = "http://172.27.35.1:7371/pic/download";
    }
    public static class ALI_API_URL{
        public static final String SEND_PHONE_MSG = "http(s)://dysmsapi.aliyuncs.com/?";
    }

    public static class API_URL{

        public static final String LOGIN_URL = "http://172.27.35.1:7371/ls/login";
        public static final String TOKEN_LOGIN_URL = "http://172.27.35.1:7371/ls/loginToken";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";


    }

    public static class WEB_SOCKET_URL{
        public static final String CHAT_URL = "ws://172.27.35.1:7371/ws/123";
    }

}
