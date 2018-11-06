package com.songshu.jucai.app.user.favorite;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.songshu.jucai.R;
import com.songshu.jucai.app.adapter.FragmentAdapter;
import com.songshu.jucai.base.BaseAc;

import me.grantland.widget.AutofitTextView;

public class FavoriteActivtiy extends BaseAc {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_favorite;
    }

    @Override
    public void init() {
        AutofitTextView actionTitle = find(R.id.action_title);
        actionTitle.setText(R.string.mine_favorite);
        find(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });


        TabLayout tabLayout = find(R.id.favorite_tab);

        ViewPager viewPager = find(R.id.favorite_vp);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.add("文章", FavoriteContentFragment.newInstance("article"));
        adapter.add("视频", FavoriteContentFragment.newInstance("video"));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void loadData() {

    }
}
