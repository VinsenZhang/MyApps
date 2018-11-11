package com.tag.man.app.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tag.man.R;
import com.tag.man.app.home.HomeFragment;
import com.tag.man.app.mail.MailFragment;
import com.tag.man.app.rank.RankFragment;
import com.tag.man.app.user.MineFragment;
import com.tag.man.base.BaseAc;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainAc extends BaseAc {

    private static final String TAG = MainAc.class.getSimpleName();

    @BindView(R.id.main_fragment_container)
    FrameLayout main_fragment_container;

    @BindView(R.id.tab_home_layout)
    LinearLayout tab_home_layout;
    @BindView(R.id.tab_home_img)
    ImageView tab_home_img;
    @BindView(R.id.tab_home_text)
    TextView tab_home_text;

    @BindView(R.id.tab_rank_layout)
    LinearLayout tab_rank_layout;
    @BindView(R.id.tab_rank_img)
    ImageView tab_rank_img;
    @BindView(R.id.tab_rank_text)
    TextView tab_rank_text;


    @BindView(R.id.tab_mail_layout)
    LinearLayout tab_mail_layout;
    @BindView(R.id.tab_mail_img)
    ImageView tab_mail_img;
    @BindView(R.id.tab_mail_text)
    TextView tab_mail_text;


    @BindView(R.id.tab_mine_layout)
    LinearLayout tab_mine_layout;
    @BindView(R.id.tab_mine_img)
    ImageView tab_mine_img;
    @BindView(R.id.tab_mine_text)
    TextView tab_mine_text;


    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private RankFragment rankFragment;
    private MailFragment mailFragment;
    private MineFragment mineFragment;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        rankFragment = new RankFragment();
        mailFragment = new MailFragment();
        mineFragment = new MineFragment();

        tab_home_layout.performClick();
    }

    @Override
    public void loadData() {

    }

    @OnClick({
            R.id.tab_home_layout,
            R.id.tab_rank_layout,
            R.id.tab_mail_layout,
            R.id.tab_mine_layout
    })
    public void onClick(View v){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (v.getId()) {
            case R.id.tab_home_layout:
                homeTabChange(fragmentTransaction);
                break;
            case R.id.tab_rank_layout:
                rankabChange(fragmentTransaction);
                break;
            case R.id.tab_mail_layout:
                mailTabChange(fragmentTransaction);
                break;
            case R.id.tab_mine_layout:
                mineTabChange(fragmentTransaction);
                break;
        }

        fragmentTransaction.commitNow();

    }



    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if (!homeFragment.isHidden()) {
            tab_home_text.setTextColor(getResources().getColor(R.color.home_tab_unselected));
            fragmentTransaction.hide(homeFragment);
        }

        if (!rankFragment.isHidden()) {
            tab_rank_text.setTextColor(getResources().getColor(R.color.home_tab_unselected));
            fragmentTransaction.hide(rankFragment);
        }

        if (!mailFragment.isHidden()) {
            tab_mail_text.setTextColor(getResources().getColor(R.color.home_tab_unselected));
            fragmentTransaction.hide(mailFragment);
        }

        if (!mineFragment.isHidden()) {
            tab_mine_text.setTextColor(getResources().getColor(R.color.home_tab_unselected));
            fragmentTransaction.hide(mineFragment);
        }

        resetTab();
    }


    private void resetTab() {
        tab_home_img.setImageResource(R.drawable.app_logo);
        tab_rank_img.setImageResource(R.drawable.app_logo);
        tab_mail_img.setImageResource(R.drawable.app_logo);
        tab_mine_img.setImageResource(R.drawable.app_logo);
    }

    private synchronized void changeTab(FragmentTransaction fragmentTransaction, Fragment
            fragment, String tag) {
        if (!fragment.isAdded() && fragmentManager.findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.main_fragment_container, fragment, tag);
        }
        fragmentTransaction.show(fragment);
    }

    private void homeTabChange(FragmentTransaction fragmentTransaction) {

        changeTab(fragmentTransaction, homeFragment, "tab_home");
        tab_home_text.setTextColor(getResources().getColor(R.color.colorAccent));
        tab_home_img.setImageResource(R.drawable.app_logo);
    }


    private void rankabChange(FragmentTransaction fragmentTransaction) {
        changeTab(fragmentTransaction, rankFragment, "tab_rank");
        tab_rank_text.setTextColor(getResources().getColor(R.color.colorAccent));
        tab_rank_img.setImageResource(R.drawable.app_logo);
    }

    private void mailTabChange(FragmentTransaction fragmentTransaction) {
        changeTab(fragmentTransaction, mailFragment, "tab_mail");
        tab_mail_text.setTextColor(getResources().getColor(R.color.colorAccent));
        tab_mail_img.setImageResource(R.drawable.app_logo);

    }

    private void mineTabChange(FragmentTransaction fragmentTransaction) {
        changeTab(fragmentTransaction, mineFragment, "tab_mine");
        tab_mine_text.setTextColor(getResources().getColor(R.color.colorAccent));
        tab_mine_img.setImageResource(R.drawable.app_logo);

    }

}
