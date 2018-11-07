package com.tag.man.app.adapter;

public enum AdpaterTypeEnum {

    ARTICLE_ONE(1, "新闻列表页面单图");


    private int type;

    private String desc;


    AdpaterTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
