package com.tag.man.http;

import android.content.Context;
import android.text.TextUtils;

import com.tag.man.BuildConfig;
import com.tag.man.MyApp;
import com.tag.man.constants.AppConstants;
import com.tag.man.mylibrary.comm.CommUtils;
import com.tag.man.mylibrary.comm.PreUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头默认参数
 * Created by vinse on 2018/9/13.
 */

public class HeadInterceptor implements Interceptor {

    private Context context;

    public HeadInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("token", PreUtils.getString(AppConstants.SPKEY.TOKEN, ""))
                .header("version", BuildConfig.VERSION_NAME)
                .header("device", CommUtils.getUniqueId(context))
                .header("imei", CommUtils.getAndroidId(context))
                .header("channel", CommUtils.getChannelName(context))
                .header("platform", "android")
                .header("current_time", String.valueOf(System.currentTimeMillis()))
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
