package com.songshu.jucai.http;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.MessageApi;

import java.util.Map;

public class MessageApiHelper {

    public static void messageList(Map<String, String> params, MyCallBack callBack){
        MessageApi messageApi = MyApp.getApp().getRetrofit().create(MessageApi.class);
        messageApi.messageList(params).enqueue(callBack);
    }


    public static void messageDetail(Map<String, String> params, MyCallBack callBack){
        MessageApi messageApi = MyApp.getApp().getRetrofit().create(MessageApi.class);
        messageApi.messageDetail(params).enqueue(callBack);
    }

}
