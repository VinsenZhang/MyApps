package com.songshu.jucai.push;

import android.content.Intent;

import com.songshu.jucai.app.main.MainAc;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class UmNotifyActivity extends UmengNotifyClickActivity {


    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);

        Intent i = new Intent(this, MainAc.class);
        i.putExtra("body", body);
        startActivity(i);
        finish();

    }

}
