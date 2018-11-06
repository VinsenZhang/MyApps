package com.songshu.jucai.app.user.message.adapter;

import android.app.Activity;
import android.text.Html;
import android.widget.TextView;

import com.songshu.jucai.R;
import com.songshu.jucai.mylibrary.comm.CommHolder;
import com.songshu.jucai.mylibrary.comm.CommRyAdapter;
import com.songshu.jucai.vo.message.MessageItemVo;

import java.util.ArrayList;

public class MessageListAdapter extends CommRyAdapter<MessageItemVo> {


    private static final int normal = 1;

    private static final int comment = 2;


    public MessageListAdapter(Activity mContext, ArrayList<MessageItemVo> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void bindData(CommHolder commHolder, MessageItemVo data, int position) {
        if (getItemViewType(position) == comment) {
            // comment replays
            commHolder.setCircleImage(mContext, R.id.icon, R.drawable.app_logo_round, data
                    .getPortrait());

            TextView comment = commHolder.getView(R.id.comment);
            comment.setText(Html.fromHtml(data.getExpand().getContent()));

        }

        TextView title = commHolder.getView(R.id.title);
        title.setText(Html.fromHtml(data.getTitle()));
        TextView content = commHolder.getView(R.id.content);
        content.setText(Html.fromHtml(data.getContent()));
        TextView time = commHolder.getView(R.id.time);
        time.setText(Html.fromHtml(data.getAdd_time()));
    }

    @Override
    protected int getLayoutRes(int viewType) {
        if (viewType == comment) {
            return R.layout.message_list_comment_item;
        } else {
            return R.layout.message_list_normal_item;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getShow_type().equals("1")) {
            return comment;
        } else {
            return normal;
        }
    }
}
