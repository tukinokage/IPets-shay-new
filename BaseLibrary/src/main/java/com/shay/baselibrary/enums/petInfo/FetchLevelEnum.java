package com.shay.baselibrary.enums.petInfo;

public enum FetchLevelEnum {
    easy(0, "容易"),
    normal(1, "一般"),
    hard(2, "困难");

    private int num;

    public String getChinese() {
        return chinese;
    }

    private String chinese;

    public int getNum() {
        return num;
    }

    FetchLevelEnum(int i, String chinese){
        this.num = i;this.chinese = chinese;
    }

    public static FetchLevelEnum getEnumByNum(int i){
        for (FetchLevelEnum fe :
        FetchLevelEnum.values()) {

            if(i == fe.num){
                return fe;
            }
        }

        return null;
    }
}
