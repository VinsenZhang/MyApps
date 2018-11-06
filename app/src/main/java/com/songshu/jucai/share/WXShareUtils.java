package com.songshu.jucai.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.net.URL;
import java.security.MessageDigest;


public class WXShareUtils {

    private static final int THUMB_SIZE = 150;

    public static void shareToFriendBigImage(Context context, boolean isFriendCircle, String imageUrl, String pageUrl, String wxId, String packageName) {
        try {

            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = pageUrl;

            WXMediaMessage msg = new WXMediaMessage(webpageObject);

            Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream());

            WXImageObject imgObj = new WXImageObject(bitmap);

            msg.mediaObject = imgObj;

            bitmap.recycle();

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = isFriendCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

            Bundle localBundle = new Bundle();
            req.toBundle(localBundle);

            Intent localIntent = new Intent();
            localIntent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
            localIntent.putExtras(localBundle);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            localIntent.putExtra("_mmessage_sdkVersion", 570490883);
            localIntent.putExtra("_mmessage_appPackage", packageName);
            localIntent.putExtra("_mmessage_content", "weixin://sendreq?appid=" + wxId);

            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("weixin://sendreq?appid=" + wxId);
            localStringBuffer.append(570490883);
            localStringBuffer.append(packageName);
            localStringBuffer.append("mMcShCsTr");
            localIntent.putExtra("_mmessage_checksum", c(localStringBuffer.toString().substring(1, 9).getBytes()).getBytes());

            context.startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分享到微信好友或朋友圈
     *
     * @param isFriendCircle
     * @param title
     * @param description
     * @param imageUrl
     * @param pageUrl
     * @param wxId
     * @param packageName
     */
    public static void shareToFriend(Context context, boolean isFriendCircle, String title, String description, String imageUrl, String pageUrl, String wxId, String packageName) {
        try {
            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = pageUrl;

            WXMediaMessage msg = new WXMediaMessage(webpageObject);
            msg.title = title;
            msg.description = description;

            Bitmap bitmap;
            Bitmap thumbBmp;
            bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream()); // 需新开线程，否则报android.os.NetworkOnMainThreadException
            thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
            bitmap.recycle();

            msg.thumbData = CommUtils.bmpToByteArray(thumbBmp, true); // 设置缩略图

            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = isFriendCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;




            Bundle localBundle = new Bundle();
            req.toBundle(localBundle);

            Intent localIntent = new Intent();
            localIntent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
            localIntent.putExtras(localBundle);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            localIntent.putExtra("_mmessage_sdkVersion", 570490883);
            localIntent.putExtra("_mmessage_appPackage", packageName);
            localIntent.putExtra("_mmessage_content", "weixin://sendreq?appid=" + wxId);

            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("weixin://sendreq?appid=" + wxId);
            localStringBuffer.append(570490883);
            localStringBuffer.append(packageName);
            localStringBuffer.append("mMcShCsTr");
            localIntent.putExtra("_mmessage_checksum", c(localStringBuffer.toString().substring(1, 9).getBytes()).getBytes());

            context.startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    public static String c(byte[] var0) {
        char[] var1 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest var2;
            (var2 = MessageDigest.getInstance("MD5")).update(var0);
            int var8;
            char[] var3 = new char[(var8 = (var0 = var2.digest()).length) * 2];
            int var4 = 0;

            for (int var5 = 0; var5 < var8; ++var5) {
                byte var6 = var0[var5];
                var3[var4++] = var1[var6 >>> 4 & 15];
                var3[var4++] = var1[var6 & 15];
            }

            return new String(var3);
        } catch (Exception var7) {
            return null;
        }
    }
}
