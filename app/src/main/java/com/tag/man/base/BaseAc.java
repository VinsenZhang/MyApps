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

public abstract class BaseAc extends SwipeBackActivity implements View.OnClickListener{

    protected Activity mActivity;


    @Subscribe
    public void onEvent(MessageEvent message){

    }

    @Override
    public void onClick(View v) {

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


    private long homeBackTime;

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean isbackground;

    @Override
    protected void onStart() {
        super.onStart();
        if (isbackground) {
            isbackground = false;

            long stayHomeTime = 0;
            if (homeBackTime != 0) {
                stayHomeTime = System.currentTimeMillis() - homeBackTime;
                homeBackTime = System.currentTimeMillis();
            }

        }
    }


    public boolean isApplicationInBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isApplicationInBackground()) {
            isbackground = true;
            homeBackTime = System.currentTimeMillis();
        }
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
