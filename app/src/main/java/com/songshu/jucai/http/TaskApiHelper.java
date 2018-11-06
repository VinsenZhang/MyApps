package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.TaskApi;

import java.util.HashMap;
import java.util.Objects;

/**
 * task api
 * Created by vinse on 2018/9/14.
 */

public class TaskApiHelper {


    /**
     * 获取红包数
     */
    public static void getRedPackageNum(MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.getRedPackageNum().enqueue(callBack);
    }

    /**
     * 分享微信朋友 分享朋友圈
     */
    public static void shareFirends(String type, MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.shareFirends(type).enqueue(callBack);
    }

    public static void shareBigImg(MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.shareBigImg().enqueue(callBack);
    }


    /**
     * 任务列表
     */
    public static void taskList( MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.taskList().enqueue(callBack);
    }

    /**
     * 任务列表
     */
    public static void generateInvitationCode(MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.generateInvitationCode().enqueue(callBack);
    }

    /**
     * 任务列表
     */
    public static void taskStatusClick(HashMap<String, Object> params, MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.taskStatusClick(params).enqueue(callBack);
    }

    /**
     * 登录显示
     */
    public static void signShow(MyCallBack callBack) {
        TaskApi taskApi = MyApp.getApp().getRetrofit().create(TaskApi.class);
        taskApi.signShow().enqueue(callBack);
    }
}
