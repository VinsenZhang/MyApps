package com.songshu.jucai.app.user.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.adapter.GlideApp;
import com.songshu.jucai.app.detail.adapter.CommendReplayListener;
import com.songshu.jucai.app.user.message.adapter.MessageDetailAdapter;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.http.CommentApiHelper;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MessageApiHelper;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.utils.ActivityJumpHelper;
import com.songshu.jucai.vo.RedPackageVo;
import com.songshu.jucai.vo.message.ExpandVo;
import com.songshu.jucai.vo.message.MessageItemVo;
import com.songshu.jucai.vo.message.MessageReplayVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageReplayDetail extends BaseAc implements View.OnClickListener {

    private TextView articleTitle;
    private ImageView articleIcon;
    private SmartRefreshLayout refreshLayout;
    private ExpandVo data;

    private ArrayList<MessageReplayVo.ListBean> listDatas = new ArrayList<>();

    private int nextPage = 1;
    private MessageDetailAdapter detailAdapter;

    private RelativeLayout sendCommentLayout;
    private TextView sendCommentSend;
    private EditText sendCommentEdit;
    private TextView replayText;

    private boolean isVideo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_message_replay_detail;
    }

    @Override
    public void init() {
        try {
            Bundle extras = getIntent().getExtras();
            data = (ExpandVo) extras.getSerializable("data");
        } catch (Exception e) {
        }

        find(R.id.img_back).setOnClickListener(this);
        TextView actionTitle = find(R.id.action_title);
        actionTitle.setText("评论详情");


        find(R.id.article_origin_layout).setOnClickListener(this);

        articleTitle = find(R.id.article_title);
        articleIcon = find(R.id.article_icon);

        refreshLayout = find(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(false);

        RecyclerView messageDetailRy = find(R.id.message_detail_ry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        messageDetailRy.setLayoutManager(layoutManager);

        detailAdapter = new MessageDetailAdapter(mActivity, listDatas);
        detailAdapter.setReplayListener(replayListener);
        messageDetailRy.setAdapter(detailAdapter);


        sendCommentLayout = find(R.id.send_comment_layout);
        TextView sendCommentCancel = find(R.id.send_comment_cancel);
        sendCommentSend = find(R.id.send_comment_send);
        sendCommentEdit = find(R.id.send_comment_edit);
        replayText = find(R.id.replay_text);

        sendCommentEdit.addTextChangedListener(sendCommentEditWatcher);

        sendCommentEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //以下方法防止两次发送请求
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            //发送请求
                            addComment();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }

        });

        // 发送
        sendCommentSend.setOnClickListener(sendListener);
        // 取消发送
        sendCommentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentLayout.setVisibility(View.GONE);
                CommUtils.hideSoftInput(mActivity);
                sendCommentSend.setTextColor(Color.parseColor("#000000"));
                sendCommentSend.setBackgroundResource(R.drawable.trabsparent_gray_stroke);
            }
        });

    }


    // 发表评论
    private final View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addComment();
        }
    };

    /**
     * 评论输入框监控字数变化
     */
    private final TextWatcher sendCommentEditWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = s.toString().length();
            if (length >= 5 && length <= 35) {
                sendCommentSend.setTextColor(Color.parseColor("#ffffff"));
                sendCommentSend.setBackgroundResource(R.drawable.gradient_red_shape);
            } else {
                sendCommentSend.setTextColor(Color.parseColor("#000000"));
                sendCommentSend.setBackgroundResource(R.drawable.trabsparent_gray_stroke);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private String replayCommendId;

    private final CommendReplayListener replayListener = new CommendReplayListener() {
        @Override
        public void clickCommend(String username, String commendId) {
            replayCommendId = commendId;
            replayText.setText("回复" + username + ":");
            sendCommentLayout.setVisibility(View.VISIBLE);
            sendCommentSend.setTextColor(Color.parseColor("#000000"));
            sendCommentSend.setBackgroundResource(R.drawable.trabsparent_gray_stroke);
            CommUtils.showSoftInput(mActivity, sendCommentEdit);
        }
    };


    private boolean isAdding = false;

    /**
     * 添加评论
     */
    private void addComment() {

        if (isAdding){
            return;
        }

        String comment = sendCommentEdit.getText().toString().trim();
        if (comment.length() < 5 || comment.length() > 35) {
            MyApp.Toast("评论不足5个字或者超过35个字不可评论");
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        if (TextUtils.isEmpty(replayCommendId)) {
            return;
        }
        params.put("reviews_id", replayCommendId);
        params.put("id", data.getAid());
        params.put("reviews", comment);

        replayCommendId = "";

        isAdding = true;

        CommentApiHelper.addComment(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {

                isAdding = false;

                sendCommentEdit.setText("");

                listDatas.clear();
                nextPage = 1;
                loadData();

                Gson gson = new Gson();
                String json = gson.toJson(response.getData());
                RedPackageVo redPackageVo = gson.fromJson(json, RedPackageVo.class);
                CommUtils.hideSoftInput(mActivity);
                sendCommentSend.setBackgroundResource(R.drawable.trabsparent_gray_stroke);
                sendCommentLayout.setVisibility(View.GONE);

                if (TextUtils.isEmpty(redPackageVo.getText()) || TextUtils.isEmpty(redPackageVo
                        .getMoney())) {
                    MyApp.Toast(response.getMessage());
                    return;
                }
                MoneyToast.showToast(redPackageVo.getText(), redPackageVo.getMoney(), false);
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                isAdding = false;
            }
        });

    }


    @Override
    public void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", data.getId());
        params.put("uid", data.getUid());
        params.put("aid", data.getAid());
        params.put("review_uid", data.getReview_uid());
        params.put("page", String.valueOf(nextPage));
        params.put("page_size", "20");

        params.put("sign", AppUtils.getSignParams(params));

        MessageApiHelper.messageDetail(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                MessageReplayVo messageReplayVo = gson.fromJson(gson.toJson(response.getData()),
                        MessageReplayVo.class);
                conertData(messageReplayVo);
            }
        });
    }

    private void conertData(MessageReplayVo messageReplayVo) {
        if (messageReplayVo.getPage().equals("1")) {
            isVideo = messageReplayVo.getArticle().getType_of_article().equals("3");
            articleTitle.setText(messageReplayVo.getArticle().getTitle());
            GlideApp.with(mActivity).load(messageReplayVo.getArticle().getPic()).into(articleIcon);
        }
        int page = Integer.valueOf(messageReplayVo.getPage());
        int totalPage = Integer.valueOf(messageReplayVo.getTotal_page());

        if (page == totalPage) {
            refreshLayout.setEnableLoadMore(false);
        } else {
            refreshLayout.setEnableLoadMore(true);
            nextPage = page + 1;
        }

        listDatas.addAll(messageReplayVo.getList());
        detailAdapter.notifyItemRangeChanged(listDatas.size() - messageReplayVo.getList().size(),
                messageReplayVo.getList().size());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_origin_layout:
                if (isVideo) {
                    ActivityJumpHelper.jumpVideoDetail(mActivity, data.getAid());
                } else {
                    ActivityJumpHelper.jumpArticleDetail(mActivity, data.getAid());
                }
                break;
            case R.id.img_back:
                mActivity.finish();
                break;
        }
    }
}
