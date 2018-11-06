package com.songshu.jucai;

import android.content.Intent;
import android.net.Uri;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.AdsApiHelper;
import com.songshu.jucai.http.AppConfigApiHelper;
import com.songshu.jucai.http.HeadInterceptor;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.push.UmengHelper;
import com.songshu.jucai.vo.ad.SplashVo;
import com.songshu.jucai.vo.config.AppConfigVo;
import com.songshu.jucai.vo.config.JumpSchemeUrlVo;
import com.umeng.commonsdk.UMConfigure;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

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



        String processName = CommUtils.getProcessName(this, Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(CommUtils.getPkgName(this));
            if (defaultProcess) {

                initRetrofit();
                initSplashAd();
                initAppConfig();
            }
        }

        CrashWoodpecker.init(this);

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, AppConstants.IDS.UM_PUSH_KEY);

        MiPushRegistar.register(this, AppConstants.IDS.MI_PUSH_APP_ID, AppConstants.IDS
                .MI_PUSH_APP_KEY);
        //华为通道
        HuaWeiRegister.register(this);

        UmengHelper.init(this);


        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void initSplashAd() {
        AdsApiHelper.getSplashAdConfig(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                SplashVo splashVo = gson.fromJson(gson.toJson(response.getData()), SplashVo.class);
                PreUtils.saveString(AppConstants.SPKEY.SPLASH_AD_TYPE, splashVo.getAd_type());
                PreUtils.saveString(AppConstants.SPKEY.SPLASH_AD_ID, splashVo.getAd_id());
            }
        });
    }


    public void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast("协议错误" + url);
        }

    }


    /**
     * 初始化app 配置
     */
    public void initAppConfig() {
        AppConfigApiHelper.getAppConfig(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                AppConfigVo configVo = gson.fromJson(gson.toJson(response.getData()), AppConfigVo
                        .class);
                PreUtils.saveBool(AppConstants.SPKEY.IS_OPEN_PHONE_LOGIN, "1".equals(configVo
                        .getPhone_login()));
                PreUtils.saveBool(AppConstants.SPKEY.IS_OPEN_COMMENT_REPLAY, "1".equals(configVo
                        .getComment_linkage()));


                String status = configVo.getTop().getStatus();

                if ("1".equals(status)) {
                    PreUtils.saveBool(AppConstants.SPKEY.IS_OPEN_HOME_TOP, true);

                    PreUtils.savaInt(AppConstants.SPKEY.HOME_TOP_SIZE, Integer.valueOf(configVo
                            .getTop().getSize()));
                } else {
                    PreUtils.saveBool(AppConstants.SPKEY.IS_OPEN_HOME_TOP, false);
                }


                if ("1".equals(configVo.getAlipay_red_envelope().getStatus())) {
                    CommUtils.copyToClipboardWithOutToast(MyApp.getApp(), configVo
                            .getAlipay_red_envelope()
                            .getPassword());
                }
            }
        });

        AppConfigApiHelper.jumpSchemeUrl(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                JumpSchemeUrlVo jumpSchemeUrlVo = gson.fromJson(gson.toJson(response.getData()),
                        JumpSchemeUrlVo.class);
                PreUtils.saveString(AppConstants.SPKEY.ABOUT_US_URL, jumpSchemeUrlVo.getInternal
                        ().getAbout_us_url());
                PreUtils.saveString(AppConstants.SPKEY.USER_PROTOCOL_URL, jumpSchemeUrlVo
                        .getInternal().getUser_protocol_url());
                PreUtils.saveString(AppConstants.SPKEY.SIGN_RULE_URL, jumpSchemeUrlVo.getInternal
                        ().getSign_rule_url());
                PreUtils.saveString(AppConstants.SPKEY.COIN_STRATEGY_URL, jumpSchemeUrlVo
                        .getInternal().getCoin_strategy_url());
                PreUtils.saveString(AppConstants.SPKEY.BINDING_WECHAT, jumpSchemeUrlVo
                        .getInternal().getBinding_WeChat());
                PreUtils.saveString(AppConstants.SPKEY.ACCOUNT_TIME_DESCRIPTION, jumpSchemeUrlVo
                        .getInternal().getAccount_time_description());
            }
        });


    }

    private void initRetrofit() {
        //声明缓存地址和大小
        Cache cache = new Cache(this.getCacheDir(), 100 * 1024 * 1024);

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(cache);

        client.addInterceptor(new HeadInterceptor());

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
