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

/**
 * 文章 评论 收藏 点赞 发表评论
 */
public interface CommentApi {

    @POST(UrlCons.Commend.comment_add)
    Call<HttpResponse> addComment(@Body Object params);

    @GET(UrlCons.Commend.comment_list)
    Call<HttpResponse> commentList(@Query("id") String id, @Query("page") String page, @Query("page_size") String page_size);

    @GET(UrlCons.Commend.comment_replay_list)
    Call<HttpResponse> commentReplayList(@Query("reviews_id") String reviews_id, @Query("page") String page, @Query("page_size") String page_size);

    @GET(UrlCons.Commend.comment_mine)
    Call<HttpResponse> mineCommentList(@Query("page") String page, @Query("page_size") String pageSize);

    @POST(UrlCons.Commend.commend_thumb_up)
    Call<HttpResponse> commentThumUp(@Body Object params);

    @POST(UrlCons.Commend.commend_delete)
    Call<HttpResponse> deleteComment(@Body Object params);

    @POST(UrlCons.Favorite.do_favorite)
    Call<HttpResponse> commentDoFavorite(@Body Object params);


    @GET(UrlCons.Favorite.favorite_list)
    Call<HttpResponse> commentFavoriteList(@QueryMap HashMap<String, Object> params);


}
