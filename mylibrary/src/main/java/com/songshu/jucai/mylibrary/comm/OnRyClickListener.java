package com.songshu.jucai.mylibrary.comm;

import android.view.View;

public interface OnRyClickListener<T> {

    void onClick(View view, T data, int position);

}
