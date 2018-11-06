package com.songshu.jucai.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.songshu.jucai.app.detail.ArticleDetailActivity;
import com.songshu.jucai.app.detail.VideoDetailActivity;
import com.songshu.jucai.app.web.WebActivity;

/**
 * Created by vinse on 2018/9/19.
 */

public class ActivityJumpHelper {

    public static void jumpArticleDetail(Context context, String aid) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("aid", aid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void jumpVideoDetail(Context context, String aid) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("aid", aid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void jumpWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void jumpAllReplayAc(Context context, String aid) {
        Intent intent = new Intent(context, ReplayCommentListAc.class);
        Bundle bundle = new Bundle();
        bundle.putString("commentId", aid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
