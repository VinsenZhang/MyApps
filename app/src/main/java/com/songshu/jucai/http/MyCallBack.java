package com.songshu.jucai.http;


import android.text.TextUtils;
import android.util.Log;

import com.songshu.jucai.MyApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求回调统一处理全局错误
 */
public abstract class MyCallBack implements Callback<HttpResponse> {


    @Override
    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
        try {
            if (TextUtils.isEmpty(response.body().getMessage())) {
                response.body().setMessage(response.body().getResult_message());
            }

            if (!TextUtils.isEmpty(response.body().getResult_code())) {
                response.body().setCode(Integer.valueOf(response.body().getResult_code()));
            }

            if (response.body().getCode() == 200) {
                onSuccessful(response.body());
            } else {
                onFail(response.body().getCode(), response.body().getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            onFail(-100, e.getMessage());
        }

    }

    @Override
    public void onFailure(Call<HttpResponse> call, Throwable t) {
        onFail(-1, t.getMessage());
    }


    public abstract void onSuccessful(HttpResponse response);


    public void onFail(int code, String message) {
        if (code != -100) {
            MyApp.Toast(message);
        }

//        if (code == 200 || code == 2016 || code == 2005) {
//            MyApp.Toast(message);
//        }
    }

}
