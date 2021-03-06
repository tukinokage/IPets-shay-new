package com.shay.baselibrary.UrlInfoUtil;

public class UrlUtil {
    public static class BASE_URL{
       // public static final String BASE_URL = "http://192.168.31.239:7371/";
        public static final String BASE_URL = "http://175.24.125.240:7371/";
        public static final String BASE_STATIC_URL = BASE_URL + "IPET_RESOURCES/";
    }

    public static class PET_PIC_URL{
        public static final String HEAD_ICON_URL = BASE_URL.BASE_STATIC_URL + "pet/head_img/";
    }

    public static class STATIC_RESOURCE{
        public static final String HEAD_ICON_URL = BASE_URL.BASE_STATIC_URL + "user/head_img/";
        public static final String BG_PIC_URL = BASE_URL.BASE_STATIC_URL + "user/bg_img/";
        public static final String POST_PIC_URL = BASE_URL.BASE_STATIC_URL + "post_img/";
        public static final String COMMENT_PIC_URL = BASE_URL.BASE_STATIC_URL + "comment_img/";
        public static final String PET_PIC_URL = BASE_URL.BASE_STATIC_URL + "pet/img/";
        public static final String PET_HEAD_PIC_URL = BASE_URL.BASE_STATIC_URL + "pet/head_img/";
        public static final String STORE_PIC_URL = BASE_URL.BASE_STATIC_URL + "store/";
        public static final String HOSPITAL_PIC_URL = BASE_URL.BASE_STATIC_URL + "hospital/";
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
        public static final String GET_COMMENT_LIST_URL = BASE_URL.BASE_URL + "usr/getCommentList";
        public static final String GET_STAR_PET_LIST_URL = BASE_URL.BASE_URL + "usr/getStarPetList";
        public static final String GET_USER_INFO_URL = BASE_URL.BASE_URL + "usr/getUserInfo";
        public static final String UPDATE_INFO_URL = BASE_URL.BASE_URL + "usr/updateInfo";
        public static final String UPDATE_BG_URL = BASE_URL.BASE_URL + "usr/updateBg";
        public static final String UPDATE_HEAD_IMG_URL = BASE_URL.BASE_URL + "usr/updateHeadImg";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";
    }


    public static class PET_URL{
        public static final String GET_PET_LIST_URL = BASE_URL.BASE_URL + "pet/petlist";
        public static final String GET_PET_INTRODUCE_URL = BASE_URL.BASE_URL + "pet/loadPetIntroduction";
        public static final String GET_PET_IMAGE_URL = BASE_URL.BASE_URL + "pet/loadPetImage";
        public static final String GET_LOAD_STORE_URL = BASE_URL.BASE_URL + "pet/loadPetStore";
        public static final String GET_LOAD_HOSPITAL_URL = BASE_URL.BASE_URL + "pet/loadPetHospital";
        public static final String STAR_PET_URL = BASE_URL.BASE_URL + "usr/starPet";
        public static final String CHECK_PET_URL = BASE_URL.BASE_URL + "usr/checkPetStar";
       // public static final String REGISTER_URL = "http://172.27.35.1:7371/ls/register";
    }

    public static class POST_URL{
        public static final String GET_POST_LIST_URL = BASE_URL.BASE_URL + "post/postlist";
        public static final String GET_POST_INFO_URL = BASE_URL.BASE_URL + "post/postInfo";
        public static final String UPLOAD_PIC_URL = BASE_URL.BASE_URL + "post/uploadPic";
        public static final String POST_NEW_URL = BASE_URL.BASE_URL + "post/commitPost";
    }

    public static class COMMENT_URL{
        public static final String GET_COMMENT_LIST_URL = BASE_URL.BASE_URL + "comment/getCommentList";
        public static final String POST_COMMENT_URL = BASE_URL.BASE_URL + "comment/postComment";
        public static final String UPLOAD_COMMENT_PIC_URL = BASE_URL.BASE_URL + "comment/uploadPic";
    }

    public static class DAILY_RECORD_URL{
        public static final String POST_DAILY_RECORD_URL = BASE_URL.BASE_URL + "usr/postDailyRecord";
        public static final String GET_DAILY_RECORD_URL = BASE_URL.BASE_URL + "usr/getDailyRecord";
    }



}
