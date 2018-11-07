package com.tag.man.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tag.man.BuildConfig;
import com.tag.man.app.main.MainAc;
import com.tag.man.mylibrary.comm.CommUtils;
import com.tag.man.mylibrary.comm.PreUtils;

public class SplashAc extends Activity {

    private static final String TAG = SplashAc.class.getSimpleName();

    int splashAdWidth;
    int splashAdHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);


        splashAdWidth = CommUtils.getScreenWith(this);
        splashAdHeight = CommUtils.getScreenHeight(this) * 4 / 5;


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
