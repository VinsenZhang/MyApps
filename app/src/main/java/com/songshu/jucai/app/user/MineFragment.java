package com.songshu.jucai.app.user;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.Gson;
import com.songshu.jucai.BuildConfig;
import com.songshu.jucai.R;
import com.songshu.jucai.adapter.GlideApp;
import com.songshu.jucai.adapter.GlideCircleTransform;
import com.songshu.jucai.app.user.bind.BindTeacherActivity;
import com.songshu.jucai.app.user.favorite.FavoriteActivtiy;
import com.songshu.jucai.app.user.message.MineMessageActivity;
import com.songshu.jucai.app.user.tudi.ShoutuActivity;
import com.songshu.jucai.app.user.tudi.TudiTabActivity;
import com.songshu.jucai.app.user.wallet.WalletActivity;
import com.songshu.jucai.app.user.withdraw.BindWithdrawActivity;
import com.songshu.jucai.app.user.question.NewerHelperActivity;
import com.songshu.jucai.base.BaseFragment;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.eventbus.EventEnum;
import com.songshu.jucai.eventbus.MessageEvent;
import com.songshu.jucai.http.AppConfigApiHelper;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.OtherHttpHelper;
import com.songshu.jucai.http.UserApiHelper;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.utils.ActivityJumpHelper;
import com.songshu.jucai.vo.ActivtiesVo;
import com.songshu.jucai.vo.config.CommInfoVo;
import com.songshu.jucai.vo.user.UserVo;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * 我的页面
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {


    private ImageView userIcon;
    private TextView userNickName;
    private TextView userId;
    private LinearLayout notLoginLayout;
    private SwipeRefreshLayout refreshLayout;

    private TextView shoutuTitle;
    private TextView shoutuSubTitle;
    private LinearLayout llLogin;
    private TextView userCoins;
    private TextView todayIncome;
    private TextView incomeFromTudi;
    private TextView customQq;

    private String qqkfKey;

    private String isOpenOrGoH5;

    private String jumpUrl;
    private ImageView mineFragmentRedDot;
    private LinearLayout mineTeacherLayout;


    private void refreshRedDot() {
        if (PreUtils.getBool(AppConstants.SPKEY.HAVE_NEW_MESSAGE, false)
                || PreUtils.getBool(AppConstants.SPKEY.HAVE_NEW_NOTIFY, false)) {
            mineFragmentRedDot.setVisibility(View.VISIBLE);
        } else {
            mineFragmentRedDot.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe
    public void onEvent(MessageEvent message) {
        if (message.getType() == EventEnum.refresh_message_red_dot.getType()
                || message.getType() == EventEnum.refresh_notify_red_dot.getType()) {
            refreshRedDot();
        }
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View contentView) {
        refreshLayout = contentView.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(refreshListener);

        userIcon = contentView.findViewById(R.id.user_icon);
        userIcon.setOnClickListener(this);
        userNickName = contentView.findViewById(R.id.user_nickname);
        userId = contentView.findViewById(R.id.user_id);

        notLoginLayout = contentView.findViewById(R.id.ll_notlogin);
        notLoginLayout.setOnClickListener(this);

        llLogin = contentView.findViewById(R.id.ll_login);

        LinearLayout total_coins_layout = contentView.findViewById(R.id.total_coins_layout);
        total_coins_layout.setOnClickListener(this);
        userCoins = contentView.findViewById(R.id.user_coins);

        LinearLayout today_income_layout = contentView.findViewById(R.id.today_income_layout);
        today_income_layout.setOnClickListener(this);
        todayIncome = contentView.findViewById(R.id.today_income);

        LinearLayout income_from_tudi_layout = contentView.findViewById(R.id
                .income_from_tudi_layout);
        income_from_tudi_layout.setOnClickListener(this);
        incomeFromTudi = contentView.findViewById(R.id.income_from_tudi);

        mineTeacherLayout = contentView.findViewById(R.id.mine_teacher_layout);
        mineTeacherLayout.setOnClickListener(this);


        LinearLayout walletLayout = contentView.findViewById(R.id.wallet_layout);
        walletLayout.setOnClickListener(this);

        LinearLayout mineCommentLayout = contentView.findViewById(R.id.mine_comment_layout);
        mineCommentLayout.setOnClickListener(this);

        LinearLayout mineFavoriteLayout = contentView.findViewById(R.id.mine_favorite_layout);
        mineFavoriteLayout.setOnClickListener(this);

        LinearLayout bindLayout = contentView.findViewById(R.id.bind_layout);
        bindLayout.setOnClickListener(this);

        LinearLayout settingLayout = contentView.findViewById(R.id.setting_layout);
        settingLayout.setOnClickListener(this);

        LinearLayout newerHelpLayout = contentView.findViewById(R.id.newer_help_layout);
        newerHelpLayout.setOnClickListener(this);

        LinearLayout contactCustomLayout = contentView.findViewById(R.id.contact_custom_layout);
        contactCustomLayout.setOnClickListener(this);

        customQq = contentView.findViewById(R.id.custom_qq);

        TextView version = contentView.findViewById(R.id.version);
        version.setText(BuildConfig.VERSION_NAME);

        final LinearLayout shoutuLayout = contentView.findViewById(R.id.shoutu_layout);
        shoutuLayout.setOnClickListener(this);
        shoutuTitle = contentView.findViewById(R.id.shoutu_title);
        shoutuSubTitle = contentView.findViewById(R.id.shoutu_subtitle);


        mineFragmentRedDot = find(contentView, R.id.mine_fragment_red_dot);

        refreshRedDot();
        getCommInfo();
        getData();

        if (!AppUtils.isToday(AppConstants.SPKEY.MINE_PAGE_DIALOG)) {
            checkAndShowActivityDialog();
        }
    }

    private void checkAndShowActivityDialog() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("width", CommUtils.getScreenWith(mActivity));
        params.put("height", CommUtils.getScreenHeight(mActivity));
        OtherHttpHelper.minePageActivity(params, new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                ActivtiesVo activtiesVo = gson.fromJson(gson.toJson(response.getData()),
                        ActivtiesVo.class);

                if ("1".equals(activtiesVo.getStatus())) {
                    new ActivtiesDialog(mActivity).show(activtiesVo);
                    PreUtils.saveLong(AppConstants.SPKEY.MINE_PAGE_DIALOG, System
                            .currentTimeMillis());
                }
            }
        });
    }

    private void getCommInfo() {
        AppConfigApiHelper.getCommInfo(new MyCallBack() {


            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();

                CommInfoVo commInfoVo = gson.fromJson(gson.toJson(response.getData()), CommInfoVo
                        .class);
                shoutuTitle.setText(Html.fromHtml("首次邀请徒弟 最高奖励<font size='28px'   " +
                        "color='#FF4D4D'><big>" +
                        commInfoVo.getReward().getReward_all() + "</big></font>元"));
                shoutuSubTitle.setText(Html.fromHtml("<font color='#FF4D4D'>徒弟收入" +
                        commInfoVo.getReward().getTc() + " " + ",徒孙收入" + commInfoVo.getReward()
                        .getTc_ts() + "永久分成</font>"));

                isOpenOrGoH5 = commInfoVo.getContact().getThere();
                jumpUrl = commInfoVo.getContact().getJump_url();

                customQq.setText(commInfoVo.getContact().getQqkf());
                qqkfKey = commInfoVo.getContact().getQqkf_key();

            }
        });

    }

    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout
            .OnRefreshListener() {
        @Override
        public void onRefresh() {
            getData();
            loadData(refresh_model);
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            MobclickAgent.onEvent(mActivity, AppConstants.MobEventKeys.enter_mine_page);
            AppConfigApiHelper.userEvent("4");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(refresh_model);
        getData();
    }

    private void resetViews() {
        notLoginLayout.setVisibility(View.VISIBLE);
        llLogin.setVisibility(View.GONE);
        userId.setVisibility(View.GONE);

        userNickName.setText("");
        userId.setText("");


        GlideApp.with(this)
                .load(R.drawable.app_logo)
                .transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .error(R.drawable.app_logo)
                .into(userIcon);
    }


    private void getData() {
        if (AppUtils.isSign()) {
            UserApiHelper.userInfoFix(new MyCallBack() {
                @Override
                public void onSuccessful(HttpResponse response) {
                    refreshLayout.setRefreshing(false);
                    Gson gson = new Gson();
                    UserVo userVo = gson.fromJson(gson.toJson(response.getData()), UserVo.class);
                    covertUserInfo(userVo);
                }
            });
        }
    }

    @Override
    public void loadData(int model) {
        if (AppUtils.isSign()) {
            UserApiHelper.userInfo(new MyCallBack() {
                @Override
                public void onSuccessful(HttpResponse response) {
                    refreshLayout.setRefreshing(false);
                    Gson gson = new Gson();
                    UserVo userVo = gson.fromJson(gson.toJson(response.getData()), UserVo.class);
                    covertMoney(userVo);

                }
            });
        } else {
            resetViews();
            refreshLayout.setRefreshing(false);
        }
    }




    private void covertMoney(UserVo userVo) {
        userCoins.setText(userVo.getUser_money());
        todayIncome.setText(userVo.getPresent_money());
        incomeFromTudi.setText(userVo.getTd_money_all());
    }

    private void covertUserInfo(UserVo userVo) {
        if (userVo == null) return;

        notLoginLayout.setVisibility(View.GONE);
        llLogin.setVisibility(View.VISIBLE);
        userId.setVisibility(View.VISIBLE);

        userNickName.setText(userVo.getNickname());
        userId.setText(userVo.getId());


        String tj_data = userVo.getTj_data();
        if ("1".equals(tj_data)){
            mineTeacherLayout.setVisibility(View.VISIBLE);
        }else {
            mineTeacherLayout.setVisibility(View.GONE);
        }


        GlideApp.with(this)
                .load(userVo.getPortrait())
                .transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .error(R.drawable.app_logo_round)
                .into(userIcon);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.total_coins_layout:
            case R.id.today_income_layout:
                jump(WalletActivity.class);
                break;
            case R.id.income_from_tudi_layout:
                jump(TudiTabActivity.class);
                break;
            case R.id.shoutu_layout:
                if (AppUtils.isSign()) {
                    jump(ShoutuActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;
            case R.id.ll_notlogin:
                if (AppUtils.isSign())
                    return;
                AppUtils.goGign(mActivity);
                break;
            case R.id.wallet_layout:
                if (AppUtils.isSign()) {
                    jump(WalletActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;
            case R.id.mine_teacher_layout:
                if (AppUtils.isSign()) {
                    jump(BindTeacherActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;
            case R.id.mine_comment_layout:
                if (AppUtils.isSign()) {
                    jump(MineMessageActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;

            case R.id.mine_favorite_layout:
                if (AppUtils.isSign()) {
                    jump(FavoriteActivtiy.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;

            case R.id.bind_layout:
                if (AppUtils.isSign()) {
                    jump(BindWithdrawActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;

            case R.id.setting_layout:
                if (AppUtils.isSign()) {
                    jump(SettingActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;

            case R.id.newer_help_layout:
                if (AppUtils.isSign()) {
                    jump(NewerHelperActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;
            case R.id.contact_custom_layout:

                if ("QQ".equals(isOpenOrGoH5)) {
                    if (TextUtils.isEmpty(qqkfKey))
                        return;
                    AppUtils.contactCustom(getContext(), qqkfKey);
                } else {
                    ActivityJumpHelper.jumpWebActivity(mActivity, jumpUrl);
                }
                break;
            case R.id.user_icon:
                if (AppUtils.isSign()) {
                    jump(UserDetailActivity.class);
                } else {
                    AppUtils.goGign(mActivity);
                }
                break;
        }
    }
}
