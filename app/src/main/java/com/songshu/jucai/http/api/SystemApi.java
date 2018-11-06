package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SystemApi {


    @POST(UrlCons.phone_yzm)
    Call<HttpResponse> phoneYzm(@Body Object params);





}

