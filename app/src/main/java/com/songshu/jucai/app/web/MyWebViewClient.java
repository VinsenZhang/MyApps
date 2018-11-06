package com.songshu.jucai.app.web;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.songshu.jucai.BuildConfig;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.mylibrary.comm.CommUtils;

import java.io.IOException;

/**
 * 统一处理 h5 内协议问题
 * Created by vinse on 2018/9/14.
 */

public class MyWebViewClient extends WebViewClient {


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (true) {

            String token ="";// PreUtils.getString(AppConstants.SPKEY.TOKEN, "");

            String version_string = "&version=" + BuildConfig.VERSION_NAME;


            if (CommUtils.isBase64(url)) {
                try {
                    url = CommUtils.base64Decoder(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (!url.contains("token=") && !TextUtils.isEmpty(token)) {
                if (url.contains("?")) {
                    url = url + "&token=" + token;
                } else {
                    url = url + "?token=" + token;
                }
            }
            if (!url.contains("version=")) {
                url = url + version_string;
            }
            MyApp.getApp().openUrl(url);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
