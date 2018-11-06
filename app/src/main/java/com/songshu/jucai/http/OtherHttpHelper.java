package com.songshu.jucai.http;

import android.text.TextUtils;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.AdsApi;
import com.songshu.jucai.http.api.HelpApi;
import com.songshu.jucai.http.api.Activitys;
import com.songshu.jucai.http.api.SystemApi;

import java.util.HashMap;

import retrofit2.http.QueryMap;

@SuppressWarnings("unchecked")
public class OtherHttpHelper {



    public static void sendSmsCode(HashMap<String, Object> params, MyCallBack callBack) {
        SystemApi systemApi = MyApp.getApp().getRetrofit().create(SystemApi.class);
        systemApi.phoneYzm(params).enqueue(callBack);
    }


    /**************************   help api   ************************************************/


    public static void newerHelperType(MyCallBack callBack) {
        HelpApi helpApi = MyApp.getApp().getRetrofit().create(HelpApi.class);
        helpApi.newerHelperType().enqueue(callBack);
    }

    public static void newerHelperList(MyCallBack callBack) {
        HelpApi helpApi = MyApp.getApp().getRetrofit().create(HelpApi.class);
        helpApi.newerHelperList().enqueue(callBack);
    }

    public static void webDelayed(String validate, MyCallBack callBack) {
        HelpApi helpApi = MyApp.getApp().getRetrofit().create(HelpApi.class);
        helpApi.webDelayed(validate).enqueue(callBack);
    }

    public static void getUrl(String url, MyCallBack callBack) {
        HelpApi helpApi = MyApp.getApp().getRetrofit().create(HelpApi.class);
        helpApi.getUrl(url).enqueue(callBack);
    }

    public static void adClick(HashMap<String, Object> params, MyCallBack callBack) {
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.nativeAd(params).enqueue(callBack);
    }

    public static void noticeAd( MyCallBack callBack) {
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.noticeAd().enqueue(callBack);
    }



    public static void loginPopop(MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.loginPopop().enqueue(callBack);
    }

    public static void loginPopCycle(MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.loginPopCycle().enqueue(callBack);
    }

    public static void minePageActivity(HashMap<String, Object> params, MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.minePageActivity(params).enqueue(callBack);
    }

    public static void taskPageActivity(HashMap<String, Object> params, MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.taskPageActivity(params).enqueue(callBack);
    }

    public static void taskBanner(MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.taskBanner().enqueue(callBack);
    }

    public static void invitingAwards(MyCallBack callBack) {
        Activitys startApi = MyApp.getApp().getRetrofit().create(Activitys.class);
        startApi.invitingAwards().enqueue(callBack);
    }

    /******************************分享**********************************************************/

    public static void sendMessageOfShare(String type, MyCallBack myCallBack) {

        if (TextUtils.isEmpty(type))
            return;

        TaskApiHelper.shareFirends(type, myCallBack);
    }

}
