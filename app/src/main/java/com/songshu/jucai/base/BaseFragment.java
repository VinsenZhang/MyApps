package com.songshu.jucai.base;


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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public abstract class BaseFragment extends Fragment {

    protected static final int refresh_model = 1;

    protected static final int load_model = 2;

    protected Activity mActivity;


    protected <T extends View> T find(View contentView, int resId){
        return contentView.findViewById(resId);
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
        View contentView = inflater.inflate(getLayoutRes(), container, false);
        initView(contentView);
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


    public abstract void init(Bundle savedInstanceState);


    public abstract void initView(View contentView);


    public abstract void loadData(int model);


    public abstract int getLayoutRes();


}
