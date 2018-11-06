package com.songshu.jucai.push;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.push.JPushVo;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

public class UmengHelper {


    public static void init(Context context) {


        PushAgent mPushAgent = PushAgent.getInstance(context);

        String uid = PreUtils.getString(AppConstants.SPKEY.KEY_USER_UID, "2");
        if (!"2".equals(uid)) {
            mPushAgent.addAlias(uid, "songshuzixun", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean isSuccess, String message) {
                }
            });
        }

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                String data = uMessage.extra.get("data");

                Gson gson = new Gson();
                JPushVo jPushVo = gson.fromJson(data, JPushVo.class);

                if (jPushVo.getWork_type().equals("1") || "3".equals(jPushVo.getWork_type())) {
                    PreUtils.saveBool(AppConstants.SPKEY.HAVE_NEW_MESSAGE, true);
                    EventBus.getDefault().post(new MessageEvent(EventEnum.refresh_message_red_dot
                            .getType()));
                } else if (jPushVo.getWork_type().equals("2")) {
                    PreUtils.saveBool(AppConstants.SPKEY.HAVE_NEW_NOTIFY, true);
                    EventBus.getDefault().post(new MessageEvent(EventEnum.refresh_notify_red_dot
                            .getType()));
                }

            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new
                UmengNotificationClickHandler() {


                    @Override
                    public void handleMessage(Context context, UMessage uMessage) {
                        super.handleMessage(context, uMessage);
                        String data = uMessage.extra.get("data");
                        Gson gson = new Gson();
                        JPushVo jPushVo = gson.fromJson(data, JPushVo.class);
                        MyApp.getApp().openUrl(jPushVo.getClick_url());
                    }

                };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });

    }

}
