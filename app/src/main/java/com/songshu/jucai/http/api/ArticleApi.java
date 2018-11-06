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

public interface ArticleApi {

    @GET(UrlCons.Article.article_list)
    Call<HttpResponse> articleList(@QueryMap HashMap<String, Object> map);

    @GET(UrlCons.Article.article_type)
    Call<HttpResponse> articleCategory();

    @GET(UrlCons.Article.video_type)
    Call<HttpResponse> videoCategory();


    @GET(UrlCons.Article.article_more_cate)
    Call<HttpResponse> articleMoreCate();

    @POST(UrlCons.Article.article_more_cate)
    Call<HttpResponse> articleSaveMoreCate(@Body Object params);

    @GET(UrlCons.Article.video_more_cate)
    Call<HttpResponse> videoMoreCate();

    @POST(UrlCons.Article.video_more_cate)
    Call<HttpResponse> videoSaveMoreCate(@Body Object params);

    @POST(UrlCons.Article.article_recommend)
    Call<HttpResponse> articleRecommend(@Body Object params);

    @GET(UrlCons.Article.article_detail)
    Call<HttpResponse> articleDetail(@Query("id") String id);


    @GET(UrlCons.Article.own_read_surplus)
    Call<HttpResponse> ownReadSurplus();

    @POST(UrlCons.Article.article_dislike)
    Call<HttpResponse> articleDislike(@Body Object params);

    @GET(UrlCons.Article.article_share)
    Call<HttpResponse> articleShare(@Query("id") String id);

    @GET(UrlCons.Article.server_time)
    Call<HttpResponse> servertime();
}
