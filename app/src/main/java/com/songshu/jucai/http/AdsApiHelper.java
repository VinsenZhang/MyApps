package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.AdsApi;

public class AdsApiHelper {



    public static void getArticleListAds(MyCallBack callBack){
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.getArticleListAds().enqueue(callBack);
    }


    public static void getVideoListAds(MyCallBack callBack){
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.getVideoListAds().enqueue(callBack);
    }

    public static void getRecommendListAds(String type, MyCallBack callBack){
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.getRecommendListAds(type).enqueue(callBack);
    }

    public static void getDetailBanners(String type, MyCallBack callBack){
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.getDetailBanners(type).enqueue(callBack);
    }


    public static void getSplashAdConfig(MyCallBack callBack){
        AdsApi adsApi = MyApp.getApp().getRetrofit().create(AdsApi.class);
        adsApi.getSplashAdConfig().enqueue(callBack);
    }

}
