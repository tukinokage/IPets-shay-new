package com.shay.baselibrary.enums.petInfo;

public enum ShapeLevelEnum {
    small(0, "小"),
    middle(1, "中"),
    large(2, "大");
    private int num;
    private String chinese;

    ShapeLevelEnum(int num, String chinese){
        this.num = num;
        this.chinese = chinese;
    }

    public int getNum() {
        return num;
    }

    public static ShapeLevelEnum getEnumByNum(int i){
        for (ShapeLevelEnum shapeLevelEnum:
             ShapeLevelEnum.values()) {

            if(shapeLevelEnum.num == i){
                return shapeLevelEnum;
            }
        }

        return null;
    }
}
