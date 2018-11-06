package com.songshu.jucai.app.user.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.mylibrary.comm.OnRyClickListener;
import com.songshu.jucai.app.user.message.adapter.NotifyListAdapter;
import com.songshu.jucai.base.BaseFragment;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MessageApiHelper;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.message.MessageItemVo;
import com.songshu.jucai.vo.message.MessageVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OfficeNotifyFragment extends BaseFragment {

    private ArrayList<MessageItemVo> datas = new ArrayList<>();
    private NotifyListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int nextPage = 1;
    private LinearLayout empty_mine_comment;

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View contentView) {
        smartRefreshLayout = find(contentView, R.id.refresh_layout);
        smartRefreshLayout.setOnRefreshListener(refreshListener);
        smartRefreshLayout.setOnLoadMoreListener(loadMoreListener);

        empty_mine_comment = find(contentView, R.id.empty_mine_comment);

        RecyclerView recyclerView = find(contentView, R.id.notify_ry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new NotifyListAdapter(mActivity, datas);

        adapter.setOnRyClickListener(new OnRyClickListener<MessageItemVo>() {
            @Override
            public void onClick(View view, MessageItemVo data, int position) {
                MyApp.getApp().openUrl(data.getClick_url());
            }
        });

        recyclerView.setAdapter(adapter);


        loadData(refresh_model);
    }

    private final OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            loadData(refresh_model);
        }
    };


    private final OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            loadData(load_model);
        }
    };


    private void refreshData(ArrayList<MessageItemVo> data) {

        smartRefreshLayout.finishRefresh();

        if (data.size() > 0) {
            empty_mine_comment.setVisibility(View.GONE);

            datas.clear();
            datas.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }


    private void loadMoreData(ArrayList<MessageItemVo> data) {

        smartRefreshLayout.finishLoadMore();

        datas.addAll(data);
        adapter.notifyItemRangeChanged(datas.size() - data.size(), data.size());
    }

    @Override
    public void loadData(int model) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", PreUtils.getString(AppConstants.SPKEY.KEY_USER_UID, "2"));
        params.put("is_system", "1");
        params.put("page_size", "20");
        params.put("page", String.valueOf(nextPage));
        params.put("sign", AppUtils.getSignParams(params));

        MessageApiHelper.messageList(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                PreUtils.saveBool(AppConstants.SPKEY.HAVE_NEW_NOTIFY, false);
                EventBus.getDefault().post(new MessageEvent(EventEnum.refresh_notify_red_dot
                        .getType()));

                Gson gson = new Gson();
                MessageVo messageVo = gson.fromJson(gson.toJson(response.getData()), MessageVo
                        .class);

                int page = Integer.valueOf(messageVo.getPage());
                int totalPage = Integer.valueOf(messageVo.getTotal_page());
                if (page == totalPage) {
                    smartRefreshLayout.setEnableLoadMore(false);
                } else {
                    smartRefreshLayout.setEnableLoadMore(true);
                    nextPage = page + 1;
                }

                if (model == refresh_model) {
                    refreshData(messageVo.getData());
                } else {
                    loadMoreData(messageVo.getData());
                }
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                if (model == refresh_model){
                    smartRefreshLayout.finishRefresh();
                }else {
                    smartRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_notify_list;
    }
}
