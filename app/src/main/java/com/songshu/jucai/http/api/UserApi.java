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

public interface UserApi {

    @GET(UrlCons.User.user_info)
    Call<HttpResponse> userInfo();


    @GET(UrlCons.User.user_info_fix)
    Call<HttpResponse> userInfoFix();

    @GET(UrlCons.User.user_detail_info)
    Call<HttpResponse> userDetail();

    @GET(UrlCons.User.teacher_info)
    Call<HttpResponse> mineTeacherInfo();


    @POST(UrlCons.User.user_save_user_info)
    Call<HttpResponse> saveUserInfo(@Body Object params);

    @POST(UrlCons.User.user_own_read)
    Call<HttpResponse> ownReadPrize(@Body  Object params);


    @POST(UrlCons.User.user_own_read_duration)
    Call<HttpResponse> ownReadDuration(@Body  Object params);


    @POST(UrlCons.User.user_read_red_package)
    Call<HttpResponse> userReadRedPackage(@Body Object params);

    @POST(UrlCons.User.user_bind_master)
    Call<HttpResponse> bindTeacher(@Body Object params);


    @GET(UrlCons.User.user_income_record)
    Call<HttpResponse> incomeRecord();

    @GET(UrlCons.User.user_income_record_list)
    Call<HttpResponse> incomeRecordList(@QueryMap HashMap<String, Object> params);

    @GET(UrlCons.User.user_withdraw_record)
    Call<HttpResponse> withdrawRecord(@QueryMap HashMap<String, Object> params);

    @POST(UrlCons.User.bind_wechat)
    Call<HttpResponse> bindWechat(@Body Object params);

    @POST(UrlCons.User.bind_alipay)
    Call<HttpResponse> bindAlipay(@Body Object params);

    @POST(UrlCons.User.unbind_alipay)
    Call<HttpResponse> unbindAlipay(@Body Object params);

    @POST(UrlCons.User.change_withdraw_way)
    Call<HttpResponse> changeWithWay(@Body Object params);

    @GET(UrlCons.User.user_bind_withdraw_way)
    Call<HttpResponse> getWithdrawInfo();

    @GET(UrlCons.User.user_apprentice)
    Call<HttpResponse> apprentice();

    @GET(UrlCons.User.user_apprentice_link)
    Call<HttpResponse> apprenticeLink();

    @GET(UrlCons.User.user_apprentice_list)
    Call<HttpResponse> apprenticeList(@QueryMap HashMap<String, Object> params);


    @POST(UrlCons.User.user_login_by_wechat)
    Call<HttpResponse> wechatLogin(@Body Object params);


    @POST(UrlCons.User.user_bind_phone_num)
    Call<HttpResponse> bindPhoneNum(@Body Object params);

    @POST(UrlCons.User.user_sign_check)
    Call<HttpResponse> userCheckSign();

    @POST(UrlCons.User.user_withdraw_detail)
    Call<HttpResponse> withdrawDetail();



    @POST(UrlCons.User.mobile_login)
    Call<HttpResponse> mobileLogin(@Body Object params);


    @POST(UrlCons.User.do_withdraw)
    Call<HttpResponse> doWithdraw(@Body Object params);


    @GET(UrlCons.User.new_user_withdraw_detail)
    Call<HttpResponse> newUserWithDetail();

    @GET(UrlCons.User.get_user_phone_num)
    Call<HttpResponse> getUserPhoneNum();



    @POST(UrlCons.User.recommend_random_redpacage)
    Call<HttpResponse> recommendRandomRedpackage();
}
