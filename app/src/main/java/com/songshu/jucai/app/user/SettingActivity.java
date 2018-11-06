package com.songshu.jucai.app.user;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.Gson;
import com.songshu.jucai.BuildConfig;
import com.songshu.jucai.MyApp;
import com.songshu.jucai.R;
import com.songshu.jucai.adapter.GlideApp;
import com.songshu.jucai.adapter.GlideCircleTransform;
import com.songshu.jucai.base.BaseAc;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.UserApiHelper;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.utils.ActivityJumpHelper;
import com.songshu.jucai.vo.user.ShifuVo;

import me.grantland.widget.AutofitTextView;

public class SettingActivity extends BaseAc implements View.OnClickListener {


    private LinearLayout mineTeacherLayout;
    private ImageView teacherIcon;
    private TextView teacherName;

    @Override
    public int getLayoutRes() {
        return R.layout.activty_user_setting;
    }

    @Override
    public void init() {
        ImageView backImg = find(R.id.img_back);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        AutofitTextView actionTitle = find(R.id.action_title);
        actionTitle.setText(R.string.user_setting);


        final LinearLayout editInfo = find(R.id.edit_info);
        editInfo.setOnClickListener(this);


        final LinearLayout about = find(R.id.about);
        about.setOnClickListener(this);


        final LinearLayout protocol = find(R.id.protocol);
        protocol.setOnClickListener(this);

        final Button button_logout = find(R.id.button_logout);
        button_logout.setOnClickListener(this);

        TextView version = find(R.id.version);
        version.setText(BuildConfig.VERSION_NAME);

        mineTeacherLayout = find(R.id.mine_teacher_layout);
        teacherIcon = find(R.id.teacher_icon);
        teacherName = find(R.id.teacher_name);

    }

    @Override
    public void loadData() {
        UserApiHelper.mineTeacherInfo(new MyCallBack() {
            @Override
            public void onSuccessful(HttpResponse response) {
                Gson gson = new Gson();
                ShifuVo shifuVo = gson.fromJson(gson.toJson(response.getData()), ShifuVo.class);
                if ("1".equals(shifuVo.getStatus())){
                    mineTeacherLayout.setVisibility(View.VISIBLE);
                    teacherName.setText(shifuVo.getTj_nickname());
                    GlideApp.with(mActivity)
                            .load(shifuVo.getTj_portrait())
                            .transition(new DrawableTransitionOptions().crossFade())
                            .transform(new GlideCircleTransform())
                            .into(teacherIcon);
                }else {
                    mineTeacherLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
            case R.id.edit_info:
                jump(UserDetailActivity.class);
                break;
            case R.id.about:
                String aboutUs = PreUtils.getString(AppConstants.SPKEY.ABOUT_US_URL, "");
                if (TextUtils.isEmpty(aboutUs)) {
                    MyApp.Toast(getResources().getString(R.string
                            .exit_and_retry_or_connect_custom));
                } else {
                    ActivityJumpHelper.jumpWebActivity(mActivity, aboutUs);
                }
                break;
            case R.id.protocol:
                String userProtocol = PreUtils.getString(AppConstants.SPKEY.USER_PROTOCOL_URL, "");
                if (TextUtils.isEmpty(userProtocol)) {
                    MyApp.Toast(getResources().getString(R.string
                            .exit_and_retry_or_connect_custom));
                } else {
                    ActivityJumpHelper.jumpWebActivity(mActivity, userProtocol);
                }
                break;
            case R.id.button_logout:
                AppUtils.logOut();
                mActivity.finish();
                break;
        }
    }
}
