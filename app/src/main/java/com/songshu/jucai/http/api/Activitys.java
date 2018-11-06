package com.songshu.jucai.http.api;


import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Activitys {


    @GET(UrlCons.Activitys.start_pop)
    Call<HttpResponse> loginPopop();

    @POST(UrlCons.Activitys.pop_login_cycle)
    Call<HttpResponse> loginPopCycle();

    @GET(UrlCons.Activitys.mine_page_activity)
    Call<HttpResponse> minePageActivity(@QueryMap HashMap<String, Object> params);


    @GET(UrlCons.Activitys.task_page_activity)
    Call<HttpResponse> taskPageActivity(@QueryMap HashMap<String, Object> params);

    @GET(UrlCons.Activitys.task_banner)
    Call<HttpResponse> taskBanner();

    @GET(UrlCons.Activitys.activity_inviting_awards)
    Call<HttpResponse> invitingAwards();

}
