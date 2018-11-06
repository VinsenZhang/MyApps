package com.songshu.jucai.base;

import com.songshu.jucai.mylibrary.comm.CommRyAdapter;

import java.util.ArrayList;

public abstract class BaseLoadMoreFragment<T> extends BaseFragment {

    protected ArrayList<T> mDatas = new ArrayList<>();

    protected CommRyAdapter<T> adapter;


    protected abstract void loadMore();


    protected void refreshData(ArrayList<T> data) {
        if (data.size() > 0) {
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
