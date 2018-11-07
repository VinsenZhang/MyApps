package com.tag.man.mylibrary.comm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class CommHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public CommHolder(@NonNull View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resId) {
        if (views.get(resId) == null) {
            View view = itemView.findViewById(resId);
            views.put(resId, view);
        }
        return (T) views.get(resId);
    }

}
