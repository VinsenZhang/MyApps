package com.songshu.jucai.share;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.adapter.GlideApp;
import com.songshu.jucai.config.AppConfig;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.TaskApiHelper;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.manager.ThreadManager;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

public class QQShareHelper {

    private Tencent tencent;

    private Activity activity;

    private Dialog dialog;

    public QQShareHelper(Activity activity) {
        this.activity = activity;
        tencent = Tencent.createInstance(AppConstants.IDS.QQ_SHARE_APPID, activity);

        dialog = new Dialog(activity, R.style.fullscreenNotTitle);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());

        View contentView = LayoutInflater.from(activity).inflate(R.layout.loading_dialog, null);

        ImageView img = contentView.findViewById(R.id.loading_img);

        GlideApp.with(activity).load(R.drawable.loading).into(img);

        dialog.setContentView(contentView);

    }



    public void shareBigImgQQ(String imgUrl) {
        try {
            String[] split = imgUrl.split("/");
            String imgLocation = AppConfig.SD_ADS_PATH + split[split.length - 1];
            CommUtils.saveFile(CommUtils.getImage(imgUrl), imgLocation);
            final Bundle params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgLocation);// 需要分享的本地图片URL
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "松鼠资讯");
            // 设置分享类型为纯图片分享
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            // 分享操作要在主线程中完成
            ThreadManager.doInMainThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    tencent.shareToQQ(activity, params, qqShareListener);
                }
            });

        } catch (Exception e) {
            Log.e("Vinsen", e.getMessage());
        }
    }

    public void shareBigImgQQZ(String imgUrl) {
        try {
            String[] split = imgUrl.split("/");
            String imgLocation = AppConfig.SD_ADS_PATH + split[split.length - 1];
            CommUtils.saveFile(CommUtils.getImage(imgUrl), imgLocation);

            final Bundle params = new Bundle();
            params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish
                    .PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
            ArrayList<String> imgs = new ArrayList<>();
            imgs.add(imgLocation);
            params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL, imgs);

            // 分享操作要在主线程中完成
            ThreadManager.doInMainThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    tencent.publishToQzone(activity, params, qqShareListener);
                }
            });
        } catch (Exception e) {
            Log.e("Vinsen", e.getMessage());
        }
    }


    public void shareQQ(String title, String desc, String imageUrl, String
            targetUrl) {
        final Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, desc);

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "松鼠资讯");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        ThreadManager.doInMainThread(new Runnable() {
            @Override
            public void run() {
                if (null != tencent) {
                    tencent.shareToQQ(activity, params, qqShareListener);
                }
            }
        });
    }


    public void shareBigImg(boolean isQQ) {
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
                    ThreadManager.doInBackGround(new Runnable() {
                        @Override
                        public void run() {
                            if (isQQ) {
                                shareBigImgQQ(decoderUrl);
                            } else {
                                shareBigImgQQZ(decoderUrl);
                            }
                        }
                    });

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


    public void shareQQZone(int shareType, String title, String summary, String imageUrl, String
            targetUrl) {
        final Bundle params = new Bundle();
        if (shareType == QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT) {
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }

        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);

        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(imageUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址

        ThreadManager.doInMainThread(new Runnable() {
            @Override
            public void run() {
                if (null != tencent) {
                    tencent.shareToQzone(activity, params, qqShareListener);
                }
            }
        });
    }

    private IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Object response) {
        }

        @Override
        public void onError(UiError e) {
            MyApp.Toast(e.errorMessage);
        }
    };
}
