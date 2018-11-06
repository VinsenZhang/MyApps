package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HelpApi {


    @Headers({
            "Cache-Control:public,max-age=1200"
    })
    @GET(UrlCons.Helps.help_type)
    Call<HttpResponse> newerHelperType();


    @Headers({
            "Cache-Control:public,max-age=1200"
    })
    @GET(UrlCons.Helps.help_type_list)
    Call<HttpResponse> newerHelperList();


    @GET(UrlCons.Helps.web_delayed)
    Call<HttpResponse> webDelayed(@Query("validate") String validate);

    @GET
    Call<HttpResponse> getUrl(@Url String url);



}
