package com.songshu.jucai.app.user.favorite;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.songshu.jucai.app.adapter.ContentListAdapter;
import com.songshu.jucai.base.BaseFragment;
import com.songshu.jucai.http.CommentApiHelper;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.utils.ActivityJumpHelper;
import com.songshu.jucai.vo.article.ArticleItemVo;
import com.songshu.jucai.vo.article.ArticleVo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteContentFragment extends BaseFragment {

    private String type;

    private SmartRefreshLayout smartRefreshLayout;

    private ContentListAdapter adapter;

    private ArrayList<ArticleItemVo> datas = new ArrayList<>();


    private int nextPage = 1;
    private LinearLayout emptyFavorite;

    public FavoriteContentFragment() {
        // Required empty public constructor
    }

    public static FavoriteContentFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString("type", type);
        FavoriteContentFragment fragment = new FavoriteContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        type = getArguments().getString("type");
    }

    @Override
    public void initView(View contentView) {
        final RecyclerView recyclerView = contentView.findViewById(R.id.mine_favorite_ry);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ContentListAdapter(mActivity, datas, false, false);
        adapter.setOnRyClickListener(new OnRyClickListener<ArticleItemVo>() {
            @Override
            public void onClick(View view, ArticleItemVo data, int position) {
                if (data.getType_of_article().equals("1") || data.getType_of_article().equals
                        ("2")) {
                    ActivityJumpHelper.jumpArticleDetail(mActivity, data.getId());
                } else if (data.getType_of_article().equals("3")) {
                    ActivityJumpHelper.jumpVideoDetail(mActivity, data.getId());
                } else {
                    MyApp.Toast("不认的type--: " + data.getType_of_article());
                }
            }
        });
        recyclerView.setAdapter(adapter);


        smartRefreshLayout = contentView.findViewById(R.id.refresh_layout);

        emptyFavorite = contentView.findViewById(R.id.empty_favorite);

        smartRefreshLayout.setOnRefreshListener(refreshListener);
        smartRefreshLayout.setOnLoadMoreListener(loadMoreListener);

        getData(refresh_model);
    }


    private final OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            getData(refresh_model);
        }
    };


    private final OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
            getData(load_model);
        }
    };


    private void getData(int model) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("current_page", model == refresh_model ? "1" : nextPage);
        params.put("page_size", 20);
        CommentApiHelper.mineFavoriteList(params, new
                MyCallBack() {
                    @Override
                    public void onSuccessful(HttpResponse response) {
                        Gson gson = new Gson();

                        ArticleVo data = gson.fromJson(gson.toJson(response.getData()), ArticleVo
                                .class);

                        int currentPage = Integer.valueOf(data.getPage());
                        int totalPage = Integer.valueOf(data.getTotal_page());

                        if (currentPage == totalPage) {
                            smartRefreshLayout.setEnableLoadMore(false);
                        } else {
                            smartRefreshLayout.setEnableLoadMore(true);
                            nextPage = currentPage + 1;
                        }


                        if (model == refresh_model) {
                            refreshData(data.getList());
                        } else {
                            loadMoreData(data.getList());
                        }
                    }

                    @Override
                    public void onFail(int code, String message) {
                        super.onFail(code, message);
                        if (model == load_model) {
                            smartRefreshLayout.finishLoadMore(false);
                        } else {
                            smartRefreshLayout.finishRefresh(false);
                        }
                    }
                });
    }


    private void loadMoreData(ArrayList<ArticleItemVo> data) {
        if (data.size() == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            datas.addAll(0, data);
            adapter.notifyItemRangeChanged(datas.size() - data.size(), data.size());
            smartRefreshLayout.finishLoadMore();
        }
    }

    private void refreshData(ArrayList<ArticleItemVo> data) {
        datas.clear();
        datas.addAll(data);
        if (datas.size() < 1) {
            emptyFavorite.setVisibility(View.VISIBLE);
            smartRefreshLayout.setVisibility(View.GONE);
        } else {
            emptyFavorite.setVisibility(View.GONE);
            smartRefreshLayout.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void loadData(int model) {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorite_content;
    }

}
