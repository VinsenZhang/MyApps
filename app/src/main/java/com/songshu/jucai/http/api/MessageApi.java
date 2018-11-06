package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageApi {

    @POST(UrlCons.Message.message_list)
    Call<HttpResponse> messageList(@Body Object params);


    @POST(UrlCons.Message.message_detail)
    Call<HttpResponse> messageDetail(@Body Object params);


}
