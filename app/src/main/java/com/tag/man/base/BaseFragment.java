package com.tag.man.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tag.man.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;

    protected View contentView;


    protected <T extends View> T find(int resId) {
        return contentView.findViewById(resId);
    }


    @Override
    public void onClick(View v) {

    }

    @Subscribe
    public void onEvent(MessageEvent message) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    protected Handler mHandler = new Handler(Looper.myLooper());

    public BaseFragment() {
        // Required empty public constructor
    }


    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutRes(), container, false);
        initView();
        return contentView;
    }

    @Subscribe
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    protected void jump(Class cls) {
        jump(cls, null);
    }

    protected void jump(Class cls, Bundle bundle) {
        Intent intent = new Intent(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void init(Bundle savedInstanceState) { }


    public abstract void initView();


    public abstract void loadData();


    public abstract int getLayoutRes();


}
