package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.CommentApi;

import java.util.HashMap;

/**
 * comment api
 * Created by vinse on 2018/9/14.
 */

public class CommentApiHelper {

    /**
     * 添加评论
     */
    public static void addComment(HashMap<String, Object> params, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.addComment(params).enqueue(callBack);
    }

    /**
     * 删除评论
     */
    public static void delComment(HashMap<String, Object> params, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.deleteComment(params).enqueue(callBack);
    }

    /**
     * 收藏文章 视频
     */
    public static void doFavorite(HashMap<String, Object> params, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.commentDoFavorite(params).enqueue(callBack);
    }

    /**
     * 我的收藏列表
     */
    public static void mineFavoriteList(HashMap<String, Object> params, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.commentFavoriteList(params).enqueue(callBack);

    }

    /**
     * 点赞
     */
    public static void doZan(HashMap<String, Object> params, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.commentThumUp(params).enqueue(callBack);
    }

    /**
     * 我的评论了列表
     */
    public static void mineCommentList(String page, String pageSize, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.mineCommentList(page, pageSize).enqueue(callBack);
    }


    /**
     * 评论列表
     */
    public static void commentList(String aid, String page, String page_size, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.commentList(aid, page, page_size).enqueue(callBack);
    }

    /**
     * 评论所有回复列表
     */
    public static void commentReplayList(String aid, String page, String page_size, MyCallBack callBack) {
        CommentApi commentApi = MyApp.getApp().getRetrofit().create(CommentApi.class);
        commentApi.commentReplayList(aid, page, page_size).enqueue(callBack);
    }

}
