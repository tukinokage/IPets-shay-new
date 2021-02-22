package com.shay.baselibrary.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Pet implements Parcelable, Serializable {
    int petClass;
    private String petId;
    private String petHeadImg;
    private String petName;
    private String petEnglishName;
    private String femaleWeight;
    private String maleWeight;
    private String originPlace;
    private int fetchLevel;
    private int shapeLevel;
    private int viewNum;

    public int getFetchLevel() {
        return fetchLevel;
    }

    public void setFetchLevel(int fetchLevel) {
        this.fetchLevel = fetchLevel;
    }

    public int getShapeLevel() {
        return shapeLevel;
    }

    public void setShapeLevel(int shapeLevel) {
        this.shapeLevel = shapeLevel;
    }



    public int getPetClass() {
        return petClass;
    }

    public void setPetClass(int petClass) {
        this.petClass = petClass;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetHeadImg() {
        return petHeadImg;
    }

    public void setPetHeadImg(String petHeadImg) {
        this.petHeadImg = petHeadImg;
    }
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetEnglishName() {
        return petEnglishName;
    }

    public void setPetEnglishName(String petEnglishName) {
        this.petEnglishName = petEnglishName;
    }

    public String getFemaleWeight() {
        return femaleWeight;
    }

    public void setFemaleWeight(String femaleWeight) {
        this.femaleWeight = femaleWeight;
    }

    public String getMaleWeight() {
        return maleWeight;
    }

    public void setMaleWeight(String maleWeight) {
        this.maleWeight = maleWeight;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(petId);
        dest.writeString(petHeadImg);
        dest.writeString(petName);
        dest.writeString(petEnglishName);
        dest.writeString(femaleWeight);
        dest.writeString(maleWeight);
        dest.writeString(originPlace);
        dest.writeInt(petClass);
        dest.writeInt(fetchLevel);
        dest.writeInt(shapeLevel);
        dest.writeInt(viewNum);

    }



    //该静态域是必须要有的，而且名字必须是CREATOR，否则会出错
     public static final Parcelable.Creator<Pet> CREATOR =
                       new Parcelable.Creator<Pet>() {

                   @Override
                   public Pet createFromParcel(Parcel source) {
                           //从Parcel读取通过writeToParcel方法写入的AppContent的相关成员信息
                           String petId = source.readString();
                           String petHeadImg = source.readString();
                           String petName = source.readString();
                           String petEnglishName = source.readString();
                           String femaleWeight = source.readString();
                           String maleWeight = source.readString();
                           String originPlace = source.readString();
                           int petClass = source.readInt();
                           int fetchLevel = source.readInt();
                           int shapeLevel = source.readInt();
                           int viewNum = source.readInt();
                           Pet pet = new Pet();
                           pet.setPetId(petId);
                           pet.setPetHeadImg(petHeadImg);
                           pet.setPetName(petName);
                           pet.setPetEnglishName(petEnglishName);
                           pet.setFemaleWeight(femaleWeight);
                           pet.setMaleWeight(maleWeight);
                           pet.setPetClass(petClass);
                           pet.setFetchLevel(fetchLevel);
                           pet.setShapeLevel(shapeLevel);
                           pet.setViewNum(viewNum);
                           pet.setOriginPlace(originPlace);

                           //更加读取到的信息，创建返回Person对象
                           return pet;
                       }

                   @Override
                   public Pet[] newArray(int size)
                   {
                           // TODO Auto-generated method stub
                           //返pet对象数组
                           return new Pet[size];
                       }
               };
}
