package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.ArticleApi;

import java.util.HashMap;

/**
 * article api
 * Created by vinse on 2018/9/14.
 */

public class ArticleApiHelper {

    /**
     * 屏蔽不喜欢的理由
     *
     * @param aid    文章
     * @param reason 不喜欢的理由
     */
    public static void dislike(String aid, String reason, MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", aid);
        params.put("reason", reason);
        articleApi.articleDislike(params).enqueue(callBack);
    }


    /**
     * 文章详情
     */
    public static void articleDetail(String aid, MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleDetail(aid).enqueue(callBack);
    }

    public static void ownReadSurplus(MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.ownReadSurplus().enqueue(callBack);
    }


    /**
     * 文章详情 推荐文章
     *
     */
    public static void articleRecommend(HashMap<String, Object> params, MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleRecommend(params).enqueue(callBack);
    }

    /**
     * 文章详情 文章分类
     */
    public static void articleCate(MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleCategory().enqueue(callBack);
    }

    public static void videoCate(MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.videoCategory().enqueue(callBack);
    }

    public static void articleMoreCate(MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleMoreCate().enqueue(callBack);
    }

    public static void videoMoreCate(MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.videoMoreCate().enqueue(callBack);
    }

    public static void saveArticleMoreCate(HashMap<String, Object> params, MyCallBack callBack){
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleSaveMoreCate(params).enqueue(callBack);
    }



    public static void saveVideoMoreCate(HashMap<String, Object> params, MyCallBack callBack){
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.videoSaveMoreCate(params).enqueue(callBack);
    }


    /**
     * 文章列表
     */
    public static void articleList(HashMap<String, Object> params, MyCallBack callBack) {
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleList(params).enqueue(callBack);
    }


    public static void articleShare(String aid, MyCallBack callBack){
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.articleShare(aid).enqueue(callBack);
    }

    public static void servertime( MyCallBack callBack){
        ArticleApi articleApi = MyApp.getApp().getRetrofit().create(ArticleApi.class);
        articleApi.servertime().enqueue(callBack);
    }


}
