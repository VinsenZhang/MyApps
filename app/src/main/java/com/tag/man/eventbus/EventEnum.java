package com.tag.man.eventbus;

public enum EventEnum {


    MAIN_TAB(1, "首页tab"),
    VIDEO_TAB(2, "视频tab"),
    TASK_TAB(3, "任务tab"),
    MINE_TAB(4, "我的tab"),

    REFRESH_ARTICLE_LIST(5,"刷新首页列表"),
    REFRESH_VIDEO_LIST(6,"刷新视频列表"),

    home_recommend(7, "首页推荐分类"),


    refresh_article_cate(8,"刷新首页文章分类"),
    refresh_video_cate(9,"刷新视频页面分类"),
    login_success(10,"登录成功"),
    refresh_task_ry(11,"刷新赚钱页面上半部分"),
    change_text_size(12,"修改文章字体大小"),
    video_detail_back_full(13,"视频退出全屏"),
    video_detail_start(14,"视频点击开始"),
    video_detail_complete(15,"视频播放完毕"),
    refresh_message_red_dot(16,"刷新消息的红点"),
    refresh_notify_red_dot(17,"刷新通知的红点"),

    ;


    private int type;


    private String desc;


    EventEnum() {
    }

    EventEnum(int type, String desc) {
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
