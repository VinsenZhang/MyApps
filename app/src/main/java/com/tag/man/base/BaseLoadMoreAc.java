package com.tag.man.base;

import com.tag.man.mylibrary.comm.CommRyAdapter;

import java.util.ArrayList;

public abstract class BaseLoadMoreAc<T> extends BaseAc {

    protected ArrayList<T> mDatas = new ArrayList<>();

    protected CommRyAdapter<T> adapter;


    protected abstract void loadMore();


    protected void refreshData(ArrayList<T> data, boolean isClear) {
        if (data.size() > 0) {
            if (isClear) {
                mDatas.clear();
            }
            mDatas.addAll(0, data);
            adapter.notifyItemRangeChanged(0, data.size());
        }
    }


    protected void loadMoreData(ArrayList<T> data) {
        if (data.size() > 0) {
            mDatas.addAll(data);
            adapter.notifyItemRangeChanged(mDatas.size() - data.size(), data.size());
        }
    }


}
