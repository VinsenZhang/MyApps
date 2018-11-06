package com.songshu.jucai.app.user.message;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.songshu.jucai.R;
import com.songshu.jucai.app.adapter.FragmentAdapter;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.mylibrary.comm.PreUtils;

import org.greenrobot.eventbus.Subscribe;

import me.grantland.widget.AutofitTextView;

public class MineMessageActivity extends BaseAc implements View.OnClickListener {

    private ImageView messageRedDot;
    private ImageView nofityRedDot;
    private String tab = "0";


    @Subscribe
    public void onEvent(MessageEvent message) {
        if (message.getType() == EventEnum.refresh_message_red_dot.getType()
                || message.getType() == EventEnum.refresh_notify_red_dot.getType()) {
            loadData();
        }
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_message;
    }

    @Override
    public void init() {
        try {
            tab = getIntent().getExtras().getString("tab");
        } catch (Exception e) {}


        find(R.id.img_back).setOnClickListener(this);
        AutofitTextView actionTitle = find(R.id.action_title);
        actionTitle.setText("我的消息中心");

        TabLayout tabLayout = find(R.id.mine_message_tab);

        ViewPager viewPager = find(R.id.mine_message_vp);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.add("消息", new MessageListFragment());
        fragmentAdapter.add("通知", new OfficeNotifyFragment());
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab mineCommentTab = tabLayout.getTabAt(0);
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout
                .mine_message_tab_content_view, null);
        TextView mineMessageTabName = contentView.findViewById(R.id.tab_name);
        mineMessageTabName.setText("消息");
        messageRedDot = contentView.findViewById(R.id.red_dot);
        mineCommentTab.setCustomView(contentView);


        TabLayout.Tab notifyTab = tabLayout.getTabAt(1);
        View notifyTabView = LayoutInflater.from(mActivity).inflate(R.layout
                .mine_message_tab_content_view, null);
        TextView nofityTabName = notifyTabView.findViewById(R.id.tab_name);
        nofityTabName.setText("通知");
        nofityRedDot = notifyTabView.findViewById(R.id.red_dot);
        notifyTab.setCustomView(notifyTabView);

        if ("2".equals(tab)) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void loadData() {
        if (PreUtils.getBool(AppConstants.SPKEY.HAVE_NEW_MESSAGE, false)) {
            messageRedDot.setVisibility(View.VISIBLE);
        } else {
            messageRedDot.setVisibility(View.INVISIBLE);
        }

        if (PreUtils.getBool(AppConstants.SPKEY.HAVE_NEW_NOTIFY, false)) {
            nofityRedDot.setVisibility(View.VISIBLE);
        } else {
            nofityRedDot.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
        }
    }
}
