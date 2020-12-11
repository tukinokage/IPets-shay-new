package com.shay.baselibrary.enums.UIFilterParams;

public enum  RankTypeEnum {
    rankday(1,"日排行"),
    rankweek(2, "周排行"),
    rankmonth(3, "月排行"),
    rankyear(4, "年排行");

    private int rankNum;
    private String rankName;

    public int getRankNum() {
        return rankNum;
    }

    public String getRankName() {
        return rankName;
    }

     RankTypeEnum(int i, String rankName){
        this.rankNum = i;
        this.rankName = rankName;
    }

    public static RankTypeEnum getRankEnumByNum(int i){
        for (RankTypeEnum r:RankTypeEnum.values()
             ) {
            if(r.rankNum == i){
                return r;
            }
        }

        return null;
    }
}
