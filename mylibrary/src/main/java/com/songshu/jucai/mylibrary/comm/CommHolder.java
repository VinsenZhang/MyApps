package com.songshu.jucai.mylibrary.comm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

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


    public void setText(int resId, String text) {
        TextView textView = getView(resId);
        textView.setText(text);
    }


    public void setImage(Context context, int resId,int defaultResId, String url) {
        ImageView imageView = getView(resId);
        GlideApp.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }


    public void setCircleImage(Context context, int resId,int defaultResId, String url){
        ImageView imageView = getView(resId);
        GlideApp.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .error(defaultResId)
                .into(imageView);
    }


    public void setImageRes(int resId, int imageRes) {
        ImageView imageView = getView(resId);
        imageView.setImageResource(imageRes);
    }

}
