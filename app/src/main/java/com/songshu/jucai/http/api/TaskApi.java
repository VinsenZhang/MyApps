package com.songshu.jucai.http.api;

import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.HttpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TaskApi {

    @GET(UrlCons.Task.task_time_red_package)
    Call<HttpResponse> getRedPackageNum();

    @Headers({
            "Cache-Control:public,max-age=120"
    })
    @POST(UrlCons.Task.task_list)
    Call<HttpResponse> taskList();


    @POST(UrlCons.Task.task_status_click_gift_id)
    Call<HttpResponse> taskStatusClick(@Body Object params);

    @GET(UrlCons.Task.task_sign_show)
    Call<HttpResponse> signShow();

    @GET(UrlCons.Task.task_share_circle_of_wechat)
    Call<HttpResponse> shareFirends(@Query("type") String type);


    @GET(UrlCons.Task.share_big_img)
    Call<HttpResponse> shareBigImg();

    @GET(UrlCons.Task.generate_invitation_code)
    Call<HttpResponse> generateInvitationCode();
}
