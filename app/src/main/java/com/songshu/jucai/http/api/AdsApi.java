package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AdsApi {


    @GET(UrlCons.Ads.ads_notice)
    Call<HttpResponse> noticeAd();

    @GET(UrlCons.Ads.ads_primordial_jump)
    Call<HttpResponse> nativeAd(@QueryMap HashMap<String, Object> params);



    @GET(UrlCons.Ads.article_list_ads)
    Call<HttpResponse> getArticleListAds();


    @GET(UrlCons.Ads.video_list_ads)
    Call<HttpResponse> getVideoListAds();


    @GET(UrlCons.Ads.recommend_ads)
    Call<HttpResponse> getRecommendListAds(@Query("type") String type);


    @GET(UrlCons.Ads.detail_banners)
    Call<HttpResponse> getDetailBanners(@Query("type") String type);


    @GET(UrlCons.Ads.splash_ad_config)
    Call<HttpResponse> getSplashAdConfig();

}
