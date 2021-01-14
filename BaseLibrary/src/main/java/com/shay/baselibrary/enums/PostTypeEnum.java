package com.shay.baselibrary.enums;

import com.shay.baselibrary.enums.UIFilterParams.RankTypeEnum;

public enum PostTypeEnum {
    all(0,"全部"),
    communication(1, "交流"),
    experience (2, "教学"),
    help(3, "求助");

    private int rankNum;
    private String rankName;

    public int getRankNum() {
        return rankNum;
    }

    public String getRankName() {
        return rankName;
    }

    PostTypeEnum(int i, String rankName){
        this.rankNum = i;
        this.rankName = rankName;
    }

    public static PostTypeEnum getRankEnumByNum(int i){
        for (PostTypeEnum r:PostTypeEnum.values()
        ) {
            if(r.rankNum == i){
                return r;
            }
        }

        return null;
    }
}
