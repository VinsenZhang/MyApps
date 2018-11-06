package com.songshu.jucai.app.video;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.baidu.mobad.feeds.NativeResponse;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.ad.loader.SongshuAdListener;
import com.songshu.jucai.ad.loader.SongshuAdLoader;
import com.songshu.jucai.ad.manager.AdClickManager;
import com.songshu.jucai.ad.view.SongShuAdView;
import com.songshu.jucai.mylibrary.comm.OnRyClickListener;
import com.songshu.jucai.app.adapter.ContentListAdapter;
import com.songshu.jucai.base.BaseFragment;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.http.AppConfigApiHelper;
import com.songshu.jucai.http.ArticleApiHelper;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.share.SharePop;
import com.songshu.jucai.utils.ActivityJumpHelper;
import com.songshu.jucai.vo.article.ArticleItemVo;
import com.songshu.jucai.vo.article.ArticleVo;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoContentFragment extends BaseFragment {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private ContentListAdapter listAdapter;
    private String cate;

    private int nextPage;

    private TextView refreshTitle;
    private View root;

    private boolean isCurrentFristPage = true;

    @Subscribe
    public synchronized void onEvent(MessageEvent message) {
        if (getUserVisibleHint()) {
            if (message.getType() == EventEnum.REFRESH_VIDEO_LIST.getType()) {
                if (!isCurrentFristPage) {
                    recyclerView.scrollToPosition(9);
                }
                recyclerView.smoothScrollToPosition(0);
                smartRefreshLayout.autoRefresh();
            }
        }
    }

    private List<ArticleItemVo> lastPageData = new ArrayList<>();

    private void refreshData(ArticleVo articleVo) {

        ArrayList<ArticleItemVo> data = articleVo.getList();

        for (ArticleItemVo articleItemVo : data) {
            if ("1".equals(articleItemVo.getAdClass())) {
                articleItemVo.setAdView(new SongShuAdView(mActivity, articleItemVo));
            }
        }

        lastPageData.clear();
        lastPageData.addAll(data);

        isCurrentFristPage = true;
        datas.addAll(0, data);
        smartRefreshLayout.finishRefresh();
        showRefreshData("「松鼠资讯」已为你刷新" + articleVo.getRefresh_quantity() + "条数据！");
        listAdapter.notifyItemRangeChanged(0, data.size());
        recyclerView.scrollToPosition(0);

    }


    void showRefreshData(String message) {
        try {
            refreshTitle.setText(message);
            mHandler.removeCallbacks(hideRunnable);
            showHead();
            mHandler.postDelayed(hideRunnable, 1500);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hideHead();
        }
    };

    private void showHead() {
        refreshTitle.clearAnimation();
        refreshTitle.setVisibility(View.VISIBLE);
        refreshTitle.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.enter_anim));
    }

    private void hideHead() {
        refreshTitle.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.exit_anim));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshTitle.setVisibility(View.GONE);
            }
        }, 300);
    }

    public static VideoContentFragment newInstance(String cate) {
        Bundle args = new Bundle();
        args.putString("cate", cate);
        VideoContentFragment fragment = new VideoContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_video_content;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (getArguments() == null) return;
        cate = getArguments().getString("cate");
    }


    private ArrayList<ArticleItemVo> datas = new ArrayList<>();

    private final Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
            smartRefreshLayout.autoRefresh();
        }
    };


    private void loadMoreData(ArticleVo articleVo) {

        ArrayList<ArticleItemVo> data = articleVo.getList();

        for (ArticleItemVo articleItemVo : data) {
            if ("1".equals(articleItemVo.getAdClass())) {
                articleItemVo.setAdView(new SongShuAdView(mActivity, articleItemVo));
            }
        }

        isCurrentFristPage = false;
        datas.addAll(data);
        smartRefreshLayout.finishLoadMore(0);

        listAdapter.notifyItemRangeChanged(datas.size() - data.size(), data.size());

    }

    private void initRecyclerView(View container) {

        recyclerView = container.findViewById(R.id.video_content_ry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listAdapter = new ContentListAdapter(mActivity, datas, false, true);
        listAdapter.setOnRyClickListener(onRyClickListener);
        listAdapter.setShareIconOnclickListener(shareClickListener);
        recyclerView.setAdapter(listAdapter);

    }

    private final OnRyClickListener<ArticleItemVo> shareClickListener = new
            OnRyClickListener<ArticleItemVo>() {
                @Override
                public void onClick(View view, ArticleItemVo data, int position) {
                    getShareParams(data.getId());
                }
            };

    private SharePop sharePop;

    /**
     * 获取分享的参数
     */
    private void getShareParams(String aid) {
        if (!AppUtils.isSign()) return;

        if (!sharePop.isShow()) {
            sharePop.showTwo(root, aid);
        }
    }

    private final OnRyClickListener<ArticleItemVo> onRyClickListener = new
            OnRyClickListener<ArticleItemVo>() {
                @Override
                public void onClick(View view, ArticleItemVo data, int position) {

                    listAdapter.notifyItemChanged(position);

                    if (data.getType_of_article().equals("1") || data.getType_of_article().equals
                            ("2")) {
                        ActivityJumpHelper.jumpArticleDetail(mActivity, data.getId());
                    } else if (data.getType_of_article().equals("3")) {
                        ActivityJumpHelper.jumpVideoDetail(mActivity, data.getId());
                    } else {
                        MyApp.Toast("不认的type : " + data.getType_of_article());
                    }
                }
            };

    private void initRefreshLayout(View contentView) {
        smartRefreshLayout = contentView.findViewById(R.id.video_content_ptr);
        smartRefreshLayout.setDragRate(1f);//显
        smartRefreshLayout.setOnRefreshListener(refreshListener);
        smartRefreshLayout.setOnLoadMoreListener(loadMoreListener);

        refreshTitle = contentView.findViewById(R.id.refresh_title);

    }


    private final OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            loadData(refresh_model);
        }
    };


    private final OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
            loadData(load_model);
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (datas.size() < 1 && smartRefreshLayout != null) {
                mHandler.postDelayed(loadRunnable, 700);
            }
        } else {
            if (smartRefreshLayout != null) {
                mHandler.removeCallbacks(loadRunnable);
            }
        }
    }


    @Override
    public void initView(View contentView) {

        initRefreshLayout(contentView);
        initRecyclerView(contentView);
        mHandler.postDelayed(loadRunnable, 500);

        sharePop = new SharePop(mActivity);
        root = contentView.findViewById(R.id.root);
    }


    private boolean isLoading = false;

    @Override
    public synchronized void loadData(int model) {
        if (isLoading) {
            return;
        }
        MobclickAgent.onEvent(mActivity, AppConstants.MobEventKeys.video_refresh);
        AppConfigApiHelper.userEvent("2");
        isLoading = true;

        HashMap<String, Object> params = new HashMap<>();
        params.put("status", "3");
        params.put("type", cate);
        params.put("page", nextPage);
        ArticleApiHelper.articleList(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                isLoading = false;
                Gson gson = new Gson();
                ArticleVo res = gson.fromJson(gson.toJson(response.getData()), ArticleVo.class);
                nextPage = Integer.valueOf(res.getPage()) + 1;
                if (model == load_model) {
                    loadMoreData(res);
                } else {
                    refreshData(res);
                }
            }

            @Override
            public void onFail(int code, String message) {
                isLoading = false;
                if (model == load_model) {
                    if (code == 2005) {
                        MyApp.Toast(message);
                    }
                    smartRefreshLayout.finishLoadMore(false);
                } else {
                    if (code == 2005) {
                        showRefreshData(message);
                    }
                    smartRefreshLayout.finishRefresh(false);
                }
            }
        });
    }
}
