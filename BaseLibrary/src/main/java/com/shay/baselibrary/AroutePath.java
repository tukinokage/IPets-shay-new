package com.shay.baselibrary;

public class AroutePath {
    public static final String PhoneActivity = "/path/phoneActivity" ;
    public static final String SetPassActivity = "/path/setPasswordActivity" ;
    public static final String MainActivity = "/path/MainActivity" ;
    public static final String  CommentActivity = "/path/commentActivity" ;
    public static final String  DailyRecordActivity = "/path/dailyRecordActivity" ;
    public static final String  PetStarActivity = "/path/petStarActivity" ;
    public static final String  UpdateInfoActivity = "/path/updateInfoActivity" ;
    public static final String  UserInfoActivity = "/path/userInfoActivity" ;
    public static final String  SearchResultActivity  = "/path/SearchResultActivity" ;
    public static final String  PetInfoActivity  = "/path/PetInfoActivity" ;
    public static final String  PostInfoActivity  = "/path/PostInfoActivity" ;

    public static class paramName{
        public static final String SEARCH_DATA_NAME = "searchCondition";
        public static final String SEARCH_ID_NAME = "searchId";
        public static final String USER_ID_PARAM_NAME = "userId";
        public static final String CURRENT_USER_STATUS = "isMyUserInfo";
        public static final String MPET = "mPet";
        public static final String POST_ID = "postId";
        public static final String RESULT_PARAM_NAME = "data";
        public static final String SET_PW_RESULT_PARAM_NAME = "result";

    }

    public static class resultCode{
        public static final int PHONE_RESULT_CODE = 1001;
        public static final int SET_PW_RESULT_CODE = 1002;
    }

    public static class requestCode{
        public static final int REQUEST_CODE_PHONE = 2001;
        public static final int REQUEST_CODE_SET_PWD = 2002;
    }
}
