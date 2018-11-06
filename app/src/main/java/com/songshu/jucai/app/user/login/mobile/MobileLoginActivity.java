package com.songshu.jucai.app.user.login.mobile;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.app.user.login.wechat.LoginActivity;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.OtherHttpHelper;
import com.songshu.jucai.http.UserApiHelper;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.user.WechatVo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class MobileLoginActivity extends BaseAc implements View.OnClickListener {

    private EditText smsCodeEdit;
    private EditText phoneEdit;
    private TextView sendSmsCode;

    private boolean canSendSmsCode = false;
    private CountDownTimer countDownTimer;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login_mobile;
    }

    @Override
    public void init() {
        ImageView imgBack = find(R.id.img_back);
        imgBack.setOnClickListener(this);

        TextView actionTitle = find(R.id.action_title);
        actionTitle.setText(R.string.mobile_login);


        sendSmsCode = find(R.id.send_sms_code);
        sendSmsCode.setOnClickListener(this);

        phoneEdit = find(R.id.phone_num_input);
        phoneEdit.addTextChangedListener(phoneWatcher);

        smsCodeEdit = find(R.id.sms_code_input);

        find(R.id.submit).setOnClickListener(this);
        find(R.id.wechat_login).setOnClickListener(this);
    }

    private final TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String trim = s.toString().trim();
            if (trim.length() > 10) {
                if (!CommUtils.isMobileNumber(s.toString().trim())) {
                    MyApp.Toast(getResources().getString(R.string.please_input_right_phone));
                } else {
                    canSendSmsCode = true;
                    sendSmsCode.setText(getResources().getString(R.string.get_sms_code));
                    sendSmsCode.setBackgroundResource(R.drawable.white_content_red_stroke);
                }
            }
        }
    };


    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
            case R.id.send_sms_code:
                if (canSendSmsCode) {
                    sendSmsCode();
                }
                break;
            case R.id.wechat_login:
                jump(LoginActivity.class);
                break;
            case R.id.submit:
                login();
                break;
        }
    }

    private void sendSmsCode() {
        String mobile = phoneEdit.getText().toString().trim();

        if (!CommUtils.isMobileNumber(mobile)) {
            MyApp.Toast(getResources().getString(R.string.please_input_right_phone));
            return;
        }

        String time = String.valueOf(System.currentTimeMillis());
        String accesstoken = "phone=" + mobile + "&time=" + time + "&key=chuangkun@136";

        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("encrypted_string", AppUtils.doubleBase64(accesstoken));
        } catch (Exception e) {
            e.printStackTrace();
        }

        OtherHttpHelper.sendSmsCode(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                sendBtnTimer();
                MyApp.Toast(response.getMessage());
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                canSendSmsCode = true;
                sendSmsCode.setText("重新发送");
                MyApp.Toast(message);
            }
        });


    }

    private void sendBtnTimer() {
        canSendSmsCode = false;
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int m = (int) (millisUntilFinished / 1000);
                sendSmsCode.setText(m + "s");
            }

            @Override
            public void onFinish() {
                canSendSmsCode = true;
                sendSmsCode.setText("重新发送");
                countDownTimer.cancel();
            }
        };

        countDownTimer.start();
    }

    private void login() {
        String smsCode = smsCodeEdit.getText().toString().trim();

        if (TextUtils.isEmpty(smsCode)) {
            MyApp.Toast("验证码不能为空");
            return;
        }


        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", phoneEdit.getText().toString().trim());
        params.put("yzm", smsCodeEdit.getText().toString().trim());
        UserApiHelper.mobileLogin(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                WechatVo result = gson.fromJson(new Gson().toJson(response.getData()), WechatVo.class);

                PreUtils.saveString(AppConstants.SPKEY.TOKEN, result.getToken());

                PreUtils.saveString(AppConstants.SPKEY.KEY_USER_UID, result.getUid());

                EventBus.getDefault().post(new MessageEvent(EventEnum.login_success.getType()));
                mActivity.finish();
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                mActivity.finish();
            }
        });

    }
}
