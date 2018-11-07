package com.tag.man.app.adapter;

import android.app.Activity;

import com.tag.man.mylibrary.comm.CommHolder;
import com.tag.man.mylibrary.comm.CommRyAdapter;

import java.util.ArrayList;

public class ContentListAdapter extends CommRyAdapter<String> {

    public ContentListAdapter(Activity mContext, ArrayList<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void bindData(CommHolder commHolder, String data, int position) {

    }

    @Override
    protected int getLayoutRes(int viewType) {
        return 0;
    }
}
