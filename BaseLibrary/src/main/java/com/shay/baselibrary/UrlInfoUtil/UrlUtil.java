package com.shay.baselibrary.UrlInfoUtil;

public class UrlUtil {
    public static class BASE_URL{
        //public static final String BASE_URL = "http://172.27.35.1:7371/";
        public static final String BASE_URL = "http://192.168.1.101:7371/";
        public static final String ALI_MSG_API_BASE_URL = "http://dysmsapi.aliyuncs.com/?";
    }

    public static class ALI_API_URL{
        public static final String SEND_PHONE_MSG = "http://dysmsapi.aliyuncs.com/?";
    }

    public static class PET_PIC_URL{
        public static final String HEAD_ICON_URL = BASE_URL.BASE_URL + "pet/getHeadImg";
    }

    public static class STATIC_RESOURCE{
        public static final String HEAD_ICON_URL = BASE_URL.BASE_URL + "pic/headicon";
        public static final String BG_PIC_URL = BASE_URL.BASE_URL + "pic/bg";
    }

    public static class USER_URL{

        public static final String LOGIN_URL = BASE_URL.BASE_URL + "usr/login";
        public static final String TOKEN_LOGIN_URL = BASE_URL.BASE_URL + "usr/loginToken";
        public static final String REGISTER_URL = BASE_URL.BASE_URL + "usr/register";
        public static final String PHONE_TOKEN_LOGIN_URL = BASE_URL.BASE_URL + "usr/loginByPhone";
        public static final String SMS_LOGIN_URL = BASE_URL.BASE_URL + "phone/sendMsg";
        public static final String CHECK_PHONE_URL = BASE_URL.BASE_URL + "usr/checkPhone";
        public static final String COMMIT_PHONE_URL = BASE_URL.BASE_URL + "phone/commitPhoneCode";
        public static final String UPDATE_PW_URL = BASE_URL.BASE_URL + "usr/updatePassword";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";
    }



    public static class PET_URL{
        public static final String GET_PET_LIST_URL = BASE_URL.BASE_URL + "pet/petlist";
        public static final String GET_PET_INTRODUCE_URL = BASE_URL.BASE_URL + "pet/loadPetIntroduction";
        public static final String GET_PET_IMAGE_URL = BASE_URL.BASE_URL + "pet/loadPetImage";
        public static final String GET_LOAD_STORE_URL = BASE_URL.BASE_URL + "pet/loadPetStore";
        public static final String GET_LOAD_HOSPITAL_URL = BASE_URL.BASE_URL + "pet/loadPetHospital";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";

    }

}
