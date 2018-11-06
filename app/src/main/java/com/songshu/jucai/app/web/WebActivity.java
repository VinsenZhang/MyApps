package com.songshu.jucai.app.web;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.manager.ThreadManager;

import java.io.IOException;

/**
 * Created by vinse on 2018/9/14.
 */

public class WebActivity extends BaseAc {

    private String url;

    private WebView webView;
    private String status;
    private String validate;
    private View root;


    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void init() {

        if (getIntent() != null && getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("url");
        } else {
            finish();
        }


        try {
            if (CommUtils.isBase64(url)) {
                url = CommUtils.base64Decoder(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.supportMultipleWindows();
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    if (title.length() > 8) {
                       // actionTitle.setText(title.substring(0, 8) + "...");
                    } else {
                       // actionTitle.setText(title);
                    }
                }
            }
        });
        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    boolean isInstall = getPackageManager().queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
                    if (isInstall) {
                        startActivity(intent);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(intent);
            }
        });
        webView.loadUrl(url);
    }




    @Override
    public void loadData() {

    }


    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();

    }

}
