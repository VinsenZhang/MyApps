package com.songshu.jucai.app.user.message.adapter;

import android.app.Activity;
import android.text.Html;
import android.widget.TextView;

import com.songshu.jucai.R;
import com.songshu.jucai.mylibrary.comm.CommHolder;
import com.songshu.jucai.mylibrary.comm.CommRyAdapter;
import com.songshu.jucai.vo.message.MessageItemVo;

import java.util.ArrayList;

public class NotifyListAdapter extends CommRyAdapter<MessageItemVo> {

    public NotifyListAdapter(Activity mContext, ArrayList<MessageItemVo> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void bindData(CommHolder commHolder, MessageItemVo data, int position) {
        TextView title = commHolder.getView(R.id.title);
        title.setText(Html.fromHtml(data.getTitle()));

        TextView time = commHolder.getView(R.id.time);
        time.setText(Html.fromHtml(data.getAdd_time()));

        TextView content = commHolder.getView(R.id.content);
        content.setText(Html.fromHtml(data.getContent()));


    }

    @Override
    protected int getLayoutRes(int viewType) {
        return R.layout.notify_list_item;
    }
}
