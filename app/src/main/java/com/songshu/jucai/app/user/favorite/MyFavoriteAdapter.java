package com.songshu.jucai.app.user.favorite;

import android.app.Activity;

import com.songshu.jucai.mylibrary.comm.CommHolder;
import com.songshu.jucai.mylibrary.comm.CommRyAdapter;
import com.songshu.jucai.vo.article.ArticleVo;

import java.util.ArrayList;

/**
 * Created by vinse on 2018/9/14.
 */

public class MyFavoriteAdapter extends CommRyAdapter<ArticleVo> {


    public MyFavoriteAdapter(Activity mContext, ArrayList<ArticleVo> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void bindData(CommHolder commHolder, ArticleVo data, int position) {

    }

    @Override
    protected int getLayoutRes(int viewType) {
        return 0;
    }
}
