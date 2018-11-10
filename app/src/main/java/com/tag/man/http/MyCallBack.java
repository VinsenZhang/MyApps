package com.tag.man.http;


import android.text.TextUtils;
import android.util.Log;

import com.tag.man.MyApp;

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
