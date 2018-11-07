package com.tag.man;

import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.tag.man.constants.UrlCons;
import com.tag.man.http.HeadInterceptor;
import com.tag.man.mylibrary.comm.PreUtils;

import java.util.concurrent.TimeUnit;

import me.drakeet.library.CrashWoodpecker;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends MultiDexApplication implements Thread.UncaughtExceptionHandler {


    private static MyApp app;

    private Retrofit retrofit;


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static MyApp getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        PreUtils.init(this);

        initRetrofit();

        initAppConfig();

        CrashWoodpecker.init(this);

        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 初始化app 配置
     */
    public void initAppConfig() {


    }

    private void initRetrofit() {
        //声明缓存地址和大小
        Cache cache = new Cache(this.getCacheDir(), 100 * 1024 * 1024);

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(cache);

        client.addInterceptor(new HeadInterceptor(this));

        OkHttpClient httpClient = client.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlCons.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

    }


    public static void Toast(String msg) {
        Toast.makeText(MyApp.getApp(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("Application", "uncaughtException : " + e.getMessage());
        Process.killProcess(Process.myPid());
        System.exit(1);
        System.gc();
    }
}
