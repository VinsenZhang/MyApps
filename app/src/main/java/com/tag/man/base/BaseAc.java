package com.tag.man.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.tag.man.eventbus.MessageEvent;
import com.tag.man.mylibrary.swipeback.SwipeBackActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseAc extends SwipeBackActivity {

    protected Activity mActivity;


    @Subscribe
    public void onEvent(MessageEvent message){
    }


    @Subscribe
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mActivity = this;

        EventBus.getDefault().register(this);
        setContentView(getLayoutRes());
        ButterKnife.bind(mActivity);
        init();
        loadData();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    protected void jump(Class cls) {
        jump(cls, null);
    }

    protected void jump(Class cls, Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void jumpForResult(Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T find(int resId) {
        return (T) findViewById(resId);
    }


    public abstract @LayoutRes
    int getLayoutRes();

    public abstract void init();

    public abstract void loadData();

}
