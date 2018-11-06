package com.songshu.jucai.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.adapter.GlideApp;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.TaskApiHelper;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.manager.ThreadManager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

public class ShareWechatHelper {

    private IWXAPI wxapi;

    private Activity activity;

    private Dialog dialog;

    public ShareWechatHelper(Activity activity) {

        this.activity = activity;

        wxapi = WXAPIFactory.createWXAPI(activity, AppConstants.IDS.WX_LOGIN_APPID, false);
        wxapi.registerApp(AppConstants.IDS.WX_LOGIN_APPID);

        dialog = new Dialog(activity, R.style.fullscreenNotTitle);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());

        View contentView = LayoutInflater.from(activity).inflate(R.layout.loading_dialog, null);

        ImageView img = contentView.findViewById(R.id.loading_img);

        GlideApp.with(activity).load(R.drawable.loading).into(img);

        dialog.setContentView(contentView);
    }


    public void shareFriend(String title, String desc, String imageUrl, String
            pageUrl) {
        ThreadManager.doInBackGround(new Runnable() {
            @Override
            public void run() {
                try {
                    share(true, title, desc, imageUrl, pageUrl);
                } catch (Exception e) {
                    MyApp.Toast("分享失败 " + e.getMessage());
                }

            }
        });
    }


    public void shareCircle(String title, String desc, String imageUrl, String
            pageUrl) {
        ThreadManager.doInBackGround(new Runnable() {
            @Override
            public void run() {
                try {
                    share(false, title, desc, imageUrl, pageUrl);
                } catch (Exception e) {
                    MyApp.Toast("分享失败 " + e.getMessage());
                }
            }
        });
    }


    public void shareBigImg(boolean isFriend){
        dialog.show();
        TaskApiHelper.shareBigImg(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                String json = gson.toJson(response.getData());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String img_url = jsonObject.getString("img_url");
                    String decoderUrl = CommUtils.base64Decoder(img_url);

                    if (isFriend) {
                       shareBigImageToCircle(decoderUrl);
                    } else {
                        shareBigImageToFriend(decoderUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                MyApp.Toast(message);
            }
        });
    }



    public void shareBigImageToFriend(String imageUrl) {
        ThreadManager.doInBackGround(new Runnable() {
            @Override
            public void run() {
                try {
                    shareBigImage(true, imageUrl);
                } catch (Exception e) {
                    MyApp.Toast("分享失败 " + e.getMessage());
                }
            }
        });
    }


    public void shareBigImageToCircle(String imageUrl) {
        ThreadManager.doInBackGround(new Runnable() {
            @Override
            public void run() {
                try {
                    shareBigImage(false, imageUrl);
                } catch (Exception e) {
                    MyApp.Toast("分享失败 " + e.getMessage());
                }
            }
        });
    }


    private void shareBigImage(boolean isFriend, String imageUrl) throws Exception {


        WXMediaMessage msg = new WXMediaMessage();

        Bitmap bitmap = CommUtils.getImage(imageUrl); //
        // 需新开线程，否则报android.os.NetworkOnMainThreadException

        msg.mediaObject = new WXImageObject(bitmap);
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WXShareUtils.buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req
                .WXSceneTimeline;

        ThreadManager.doInMainThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });

        wxapi.sendReq(req);
    }


    private void share(boolean isFriend, String title, String desc, String imageUrl, String
            pageUrl) throws Exception {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = pageUrl;

        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = desc;

        Bitmap bitmap = CommUtils.getImage(imageUrl);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);

        // 需新开线程，否则报android.os.NetworkOnMainThreadException

        bitmap.recycle();

        msg.thumbData = CommUtils.bmpToByteArray(thumbBmp, true); // 设置缩略图

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WXShareUtils.buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req
                .WXSceneTimeline;

        wxapi.sendReq(req);
    }


}
