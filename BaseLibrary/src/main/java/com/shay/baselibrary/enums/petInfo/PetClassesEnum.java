package com.shay.baselibrary.enums.petInfo;

public enum PetClassesEnum {
    //0为全部
    cat(1, "猫类"),
    dog(2, "犬类"),
    mouse(3, "啮齿类"),
    bird(4, "鸟类"),
    fish(5, "水生类"),
    crawling(6, "爬行类"),
    amphibian(7, "两栖类"),
    other(8, "其他");


    private int num;
    private String chinese;

    public String getChinese() {
        return chinese;
    }
    PetClassesEnum(int num, String chinese){
        this.num = num;
        this.chinese = chinese;
    }

    public int getNum() {
        return num;
    }

    public static PetClassesEnum getEnumByNum(int i){
        for (PetClassesEnum shapeLevelEnum:
                PetClassesEnum.values()) {

            if(shapeLevelEnum.num == i){
                return shapeLevelEnum;
            }
        }

        return null;
    }

}
