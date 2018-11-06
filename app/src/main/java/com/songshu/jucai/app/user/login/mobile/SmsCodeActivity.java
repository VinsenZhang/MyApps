package com.songshu.jucai.app.user.login.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.app.user.bind.BindMobileActivity;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.OtherHttpHelper;
import com.songshu.jucai.inter.StringResListener;
import com.songshu.jucai.mylibrary.comm.CommUtils;

import java.util.HashMap;

public class SmsCodeActivity extends BaseAc implements View.OnClickListener {

    private EditText edittextPhone;
    private String unbindStyle;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sms_code;
    }

    @Override
    public void init() {
        try {
            unbindStyle = getIntent().getExtras().getString("style");
        } catch (Exception e) {

        }

        ImageView imgBack = find(R.id.img_back);
        imgBack.setOnClickListener(this);


        edittextPhone = find(R.id.edittext_phone);

        Button submit = find(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void loadData() {

    }

    private void sendSmsCode() {
        String mobile = edittextPhone.getText().toString().trim();

        if (!CommUtils.isMobileNumber(mobile)) {
            MyApp.Toast(getResources().getString(R.string.please_input_right_phone));
            return;
        }

        AppUtils.getServerTime(new StringResListener() {
            @Override
            public void result(String res) {

                String accesstoken = "phone=" + mobile + "&time=" + res + "&key=chuangkun@136";

                HashMap<String, Object> params = new HashMap<>();
                try {
                    params.put("encrypted_string", AppUtils.doubleBase64(accesstoken));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                OtherHttpHelper.sendSmsCode(params, new MyCallBack() {
                    @Override
                    public void onSuccessful(HttpResponse response) {
                        goNext(mobile);
                        MyApp.Toast(response.getMessage());
                    }

                    @Override
                    public void onFail(int code, String message) {
                        super.onFail(code, message);
                        MyApp.Toast(message);
                    }
                });
            }
        });
    }


    private void goNext(String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        jump(BindMobileActivity.class, bundle);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
            case R.id.submit:
                sendSmsCode();
                break;
        }
    }
}
