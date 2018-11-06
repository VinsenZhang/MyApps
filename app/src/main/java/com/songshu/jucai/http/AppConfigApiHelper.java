package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.AppConfigApi;
import com.songshu.jucai.mylibrary.comm.CommUtils;

public class AppConfigApiHelper {

    public static void getDislikeReason(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.getDislikeReason().enqueue(callBack);
    }


    public static void getAppConfig(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.getAppConfig(CommUtils.getUniqueId(MyApp.getApp())).enqueue(callBack);
    }


    public static void getShareInfo(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.shareInfo().enqueue(callBack);
    }


    public static void getCommInfo(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.getCommInfo().enqueue(callBack);
    }

    public static void jumpSchemeUrl(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.jumpSchemeUrl().enqueue(callBack);
    }

    public static void appUpdateUrl(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.appUpdateUrl().enqueue(callBack);
    }

    public static void getRandomInviteCode(MyCallBack callBack) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.getRandomInviteCode().enqueue(callBack);
    }


    public static void userEvent(String type) {
        AppConfigApi appConfigApi = MyApp.getApp().getRetrofit().create(AppConfigApi.class);
        appConfigApi.userEvent(type).enqueue(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {

            }
        });
    }
}
