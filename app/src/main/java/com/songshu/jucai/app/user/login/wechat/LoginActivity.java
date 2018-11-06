package com.songshu.jucai.app.user.login.wechat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.app.user.login.mobile.MobileLoginActivity;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.grantland.widget.AutofitTextView;

/**
 * 登陆页面
 * Created by vinse on 2018/9/14.
 */

public class LoginActivity extends BaseAc implements View.OnClickListener {

    private IWXAPI api;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        ImageView imgBack = find(R.id.img_back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);

        AutofitTextView actionTitle = find(R.id.action_title);
        actionTitle.setText(R.string.wechat_login);

        TextView loginBtn = find(R.id.button_login);
        loginBtn.setOnClickListener(this);

        LinearLayout change_login_style_layout = find(R.id.change_login_style_layout);

        if (PreUtils.getBool(AppConstants.SPKEY.IS_OPEN_PHONE_LOGIN, false)) {
            change_login_style_layout.setVisibility(View.VISIBLE);
        } else {
            change_login_style_layout.setVisibility(View.GONE);
        }


        ImageView mobile_login = find(R.id.mobile_login);
        mobile_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(MobileLoginActivity.class);
            }
        });
    }

    @Override
    public void loadData() {
        api = WXAPIFactory.createWXAPI(this, AppConstants.IDS.WX_LOGIN_APPID, true);
        api.registerApp(AppConstants.IDS.WX_LOGIN_APPID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
            case R.id.button_login:
                loginWithWeChat();
                break;
        }
    }


    void loginWithWeChat() {
        if (!api.isWXAppInstalled()) {
            MyApp.Toast(getResources().getString(R.string.please_go_to_install_wechat));
            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);

        finish();
    }


}
