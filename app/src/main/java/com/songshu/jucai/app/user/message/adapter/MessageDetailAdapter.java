package com.songshu.jucai.app.user.message.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.songshu.jucai.R;
import com.songshu.jucai.mylibrary.comm.CommHolder;
import com.songshu.jucai.mylibrary.comm.CommRyAdapter;
import com.songshu.jucai.app.detail.adapter.CommendReplayListener;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.message.MessageReplayVo;

import java.util.ArrayList;

public class MessageDetailAdapter extends CommRyAdapter<MessageReplayVo.ListBean> {

    private CommendReplayListener replayListener;

    public void setReplayListener(CommendReplayListener replayListener) {
        this.replayListener = replayListener;
    }

    public MessageDetailAdapter(Activity mContext, ArrayList<MessageReplayVo.ListBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void bindData(CommHolder commHolder, MessageReplayVo.ListBean data, int position) {
        TextView fromName = commHolder.getView(R.id.from_name);
        fromName.setText(Html.fromHtml(data.getFrom_name()));

        commHolder.setCircleImage(mContext, R.id.icon, R.drawable.app_logo, data.getPortrait());

        TextView toName = commHolder.getView(R.id.to_name);
        View replayTips = commHolder.getView(R.id.replay_tips);
        if (TextUtils.isEmpty(data.getTo_name())) {
            replayTips.setVisibility(View.GONE);
            toName.setVisibility(View.GONE);
        } else {
            toName.setVisibility(View.VISIBLE);
            replayTips.setVisibility(View.VISIBLE);
            toName.setText(Html.fromHtml(data.getTo_name()));
        }

        TextView content = commHolder.getView(R.id.content);
        content.setText(Html.fromHtml(data.getReviews()));
        TextView time = commHolder.getView(R.id.comment_time);
        time.setText(Html.fromHtml(data.getAdd_time()));

        boolean ismyself = data.getUid().equals(PreUtils.getString(AppConstants.SPKEY
                .KEY_USER_UID, "2"));

        View replayView = commHolder.getView(R.id.replay);

        replayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replayListener != null){
                    replayListener.clickCommend(data.getFrom_name(), data.getId());
                }
            }
        });

        if (ismyself) {
            replayView.setVisibility(View.GONE);
        } else {
            replayView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected int getLayoutRes(int viewType) {
        return R.layout.message_detail_list_item;
    }
}
