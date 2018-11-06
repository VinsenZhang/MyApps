package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AppConfigApi {


    @GET(UrlCons.AppConifg.dislike_reason)
    Call<HttpResponse> getDislikeReason();

    @GET(UrlCons.AppConifg.app_config)
    Call<HttpResponse> getAppConfig(@Query("unique_id") String unique_id);


    @GET(UrlCons.AppConifg.apprentice_info)
    Call<HttpResponse> shareInfo();



    @GET(UrlCons.AppConifg.comm_info)
    Call<HttpResponse> getCommInfo();

    @GET(UrlCons.AppConifg.jump_scheme_url)
    Call<HttpResponse> jumpSchemeUrl();

    @GET(UrlCons.AppConifg.app_update_url)
    Call<HttpResponse> appUpdateUrl();

    @GET(UrlCons.AppConifg.getRandomInviteCode)
    Call<HttpResponse> getRandomInviteCode();


    @GET(UrlCons.AppConifg.user_event_statistics)
    Call<HttpResponse> userEvent(@Query("type") String type);
}
