package com.songshu.jucai.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.songshu.jucai.BuildConfig;
import com.songshu.jucai.app.main.MainAc;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;

public class SplashAc extends Activity {

    private static final String TAG = SplashAc.class.getSimpleName();

    int splashAdWidth;
    int splashAdHeight;
    private String adId;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);


        splashAdWidth = CommUtils.getScreenWith(this);
        splashAdHeight = CommUtils.getScreenHeight(this) * 4 / 5;

        type = PreUtils.getString(AppConstants.SPKEY.SPLASH_AD_TYPE, "baidu");
        adId = PreUtils.getString(AppConstants.SPKEY.SPLASH_AD_ID, AppConstants.IDS
                .ADS_ID_SPLASH);

    }




    private void goNext() {
        if (PreUtils.getBool(BuildConfig.VERSION_NAME, false)) {
            startActivity(new Intent(this, MainAc.class));
        } else {
            startActivity(new Intent(this, GuideAc.class));
        }
        finish();
    }

}
