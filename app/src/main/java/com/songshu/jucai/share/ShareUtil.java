package com.songshu.jucai.share;

import android.app.Activity;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.config.AppConfig;
import com.songshu.jucai.mylibrary.manager.ThreadManager;

import java.util.List;
import java.util.Set;

public class ShareUtil {

    public static void share(Activity activity, Parameters mParameters, Set<String> keys) {

        ShareWechatHelper shareWechatHelper = new ShareWechatHelper(activity);

        String title = "";
        String desc = "";
        String img = "";
        String pageUrl = "";
        boolean isCircle = false;
        boolean isImage = false;
        for (String key : keys) {
            if (!key.equals("name")) {
                String value = mParameters.getParameter(key);

                if (key.equalsIgnoreCase("type")) {
                    isCircle = value.equals("friend");
                }

                if (key.equalsIgnoreCase("image")) {
                    isImage = value.equals("1");

                }
                if (key.equalsIgnoreCase("title")) {
                    title = value;
                }
                if (key.equalsIgnoreCase("description")) {
                    desc = value;
                }
                if (key.equalsIgnoreCase("imageUrl")) {
                    img = value;
                }
                if (key.equalsIgnoreCase("pageUrl")) {
                    pageUrl = value;
                }
            }
        }

        if (isImage) {
            shareWechatHelper.shareBigImg(isCircle);
            //shareBigImage(shareWechatHelper, isCircle);
        } else {
            if (isCircle) {
                shareWechatHelper.shareCircle(title, desc, img, pageUrl);
            } else {
                shareWechatHelper.shareFriend(title, desc, img, pageUrl);
            }
        }

    }


    public static void wbShareBigImage(final boolean isFriendCircle, final String imageUrl, final
    String pageUrl) {


        if (!AppUtils.isAppAvilibleSimple(MyApp.getApp(), AppConfig.WX_PACKAGE_NAME)) {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string.please_install_wx_first));
            return;
        }


        List<Integer> indexList = AppUtils.isCanShare(MyApp.getApp());

        if (indexList.size() < 1) {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string
                    .please_install_qq_browser_first));
            return;
        }

        int lengh = indexList.size() - 1;

        int index = (int) (Math.random() * lengh);


        if (index != -1) {

            index = indexList.get(index);

            //doShare(imageUrl, title, description, imageUrl, pageUrl, AppConfigApi
            // .WXAPPIDID[index], AppConfigApi.PACKAGENAME[index]);

            final String appID = AppConfig.WXAPPIDID[index];
            final String packageName = AppConfig.PACKAGENAME[index];

            ThreadManager.doInBackGround(new Runnable() {
                @Override
                public void run() {
                    WXShareUtils.shareToFriendBigImage(MyApp.getApp(), isFriendCircle, imageUrl,
                            pageUrl, appID, packageName);
                }
            });


        } else {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string
                    .please_install_qq_browser_first));
        }
    }


    public static void wbShare(boolean isFriendCircle, String title, String description, String
            imageUrl, String pageUrl) {


        if (!AppUtils.isAppAvilibleSimple(MyApp.getApp(), AppConfig.WX_PACKAGE_NAME)) {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string.please_install_wx_first));
            return;
        }


        List<Integer> indexList = AppUtils.isCanShare(MyApp.getApp());

        if (indexList.size() < 1) {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string
                    .please_install_qq_browser_first));
            return;
        }

        int lengh = indexList.size() - 1;

        int index = (int) (Math.random() * lengh);


        if (index != -1) {

            index = indexList.get(index);
            doShare(isFriendCircle, title, description, imageUrl, pageUrl, AppConfig
                    .WXAPPIDID[index], AppConfig.PACKAGENAME[index]);
        } else {
            MyApp.Toast(MyApp.getApp().getResources().getString(R.string
                    .please_install_qq_browser_first));
        }
    }

    private static void doShare(final boolean isFriendCircle, final String title, final String
            description, final String imageUrl, final String pageUrl, final String wxId, final
                                String packageName) {
        ThreadManager.doInBackGround(new Runnable() {
            @Override
            public void run() {
                WXShareUtils.shareToFriend(MyApp.getApp(), isFriendCircle, title, description,
                        imageUrl, pageUrl, wxId, packageName);
            }
        });

    }

}
