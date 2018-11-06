package com.songshu.jucai.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.app.user.bind.BindMobileActivity;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.UserApiHelper;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.user.WechatVo;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    private static final String COUNTRY_CODE = "+86";

    private static final String RETURN_CODE = "200";

    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        //注册API
        api = WXAPIFactory.createWXAPI(this, AppConstants.IDS.WX_LOGIN_APPID, false);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }


    @Override
    public void onResp(BaseResp _resp) {
        SendAuth.Resp resp = null;

        if (_resp.getType() == 2) {
            finish();
            return;
        }

        if (_resp instanceof SendAuth.Resp) {
            resp = (SendAuth.Resp) _resp;
        }
        if (resp == null) {
            finish();
            return;
        }

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                getOpenIDThread(resp.code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                MyApp.Toast("用户主动取消登录，请重新登录");
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void getOpenIDThread(String code) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);

        UserApiHelper.wechatLogin(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                WechatVo result = gson.fromJson(gson.toJson(response.getData()), WechatVo
                        .class);
                String token = result.getToken();

                if (result.getBinding().equals("1")) {
                    Intent intent = new Intent(WXEntryActivity.this, BindMobileActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("token", token);
                    intent.putExtras(b);
                    startActivity(intent);
                    WXEntryActivity.this.finish();
                } else {
                    PreUtils.saveString(AppConstants.SPKEY.TOKEN, token);
                    PreUtils.saveString(AppConstants.SPKEY.KEY_USER_UID, result.getUid());
                    EventBus.getDefault().post(new MessageEvent(EventEnum.login_success.getType()));
                    EventBus.getDefault().post(new MessageEvent(EventEnum.refresh_task_ry.getType()));
                    WXEntryActivity.this.finish();
                }
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                WXEntryActivity.this.finish();
            }
        });
    }


    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer();
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
    }

}