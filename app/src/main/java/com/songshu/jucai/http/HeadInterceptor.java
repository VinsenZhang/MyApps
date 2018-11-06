package com.songshu.jucai.http;

import android.text.TextUtils;

import com.songshu.jucai.BuildConfig;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头默认参数
 * Created by vinse on 2018/9/13.
 */

public class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String notLoggedIn = PreUtils.getString(AppConstants.SPKEY.KEY_USER_UID, "NotLoggedIn");
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("token", PreUtils.getString(AppConstants.SPKEY.TOKEN, ""))
                .header("version", BuildConfig.VERSION_NAME)
                .header("device", CommUtils.getUniqueId(MyApp.getApp()))
                .header("imei", CommUtils.getAndroidId(MyApp.getApp()))
                .header("channel", AppUtils.getChannelName())
                .header("source", AppUtils.getChannelName())
                .header("not_logged_in", TextUtils.isEmpty(notLoggedIn) ? "NotLoggedIn" :
                        notLoggedIn)
                .header("platform", "android")
                .header("current_time", String.valueOf(System.currentTimeMillis()))
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
