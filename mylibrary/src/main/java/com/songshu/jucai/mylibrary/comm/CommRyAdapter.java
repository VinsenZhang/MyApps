package com.songshu.jucai.mylibrary.comm;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class CommRyAdapter<T> extends RecyclerView.Adapter<CommHolder> {

    protected ArrayList<T> mDatas = new ArrayList<>();

    protected Activity mContext;

    private LayoutInflater inflater;


    private OnRyClickListener<T> onRyClickListener;

    public void setOnRyClickListener(OnRyClickListener<T> onRyClickListener) {
        this.onRyClickListener = onRyClickListener;
    }

    public CommRyAdapter(Activity mContext, ArrayList<T> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public CommHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = inflater.inflate(getLayoutRes(i), viewGroup, false);
        return new CommHolder(contentView);
    }


    @Override
    public void onBindViewHolder(@NonNull CommHolder commHolder, final int i) {
        bindData(commHolder, mDatas.get(i), i);

        commHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRyClickListener != null) {
                    onRyClickListener.onClick(v, mDatas.get(i), i);
                }
            }
        });

    }

    public void insert(T data, int position) {
        mDatas.add(position, data);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    public void delete(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, 1 + mDatas.size() - position);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    protected abstract void bindData(CommHolder commHolder, T data, int position);

    protected abstract int getLayoutRes(int viewType);
}
