package com.example.usermodule.entity;

public class UserInfo {

    private String userName;
    private String userSign;
    private String dailyNum;
    private String postNum;
    private String headPicName;
    private String bgPicName;
    private int sex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getDailyNum() {
        return dailyNum;
    }

    public void setDailyNum(String dailyNum) {
        this.dailyNum = dailyNum;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }

    public String getHeadPicName() {
        return headPicName;
    }

    public void setHeadPicName(String headPicName) {
        this.headPicName = headPicName;
    }

    public String getBgPicName() {
        return bgPicName;
    }

    public void setBgPicName(String bgPicName) {
        this.bgPicName = bgPicName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
