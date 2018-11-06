package com.songshu.jucai.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.songshu.jucai.R;
import com.songshu.jucai.app.web.WebActivity;
import com.songshu.jucai.constants.AppConstants;
import com.songshu.jucai.constants.UrlCons;
import com.songshu.jucai.http.ArticleApiHelper;
import com.songshu.jucai.http.HttpResponse;
import com.songshu.jucai.http.MyCallBack;
import com.songshu.jucai.http.UserApiHelper;
import com.songshu.jucai.mylibrary.comm.CommUtils;
import com.songshu.jucai.mylibrary.comm.PreUtils;
import com.songshu.jucai.vo.share.ApprenticeVo;
import com.songshu.jucai.vo.share.ShareVo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 分享 弹框
 * Created by vinse on 2018/9/15.
 */

public class SharePop implements View.OnClickListener {


    private PopupWindow popupWindow;

    private Activity context;
    private final LinearLayout bottomShareLayout;

    private QQShareHelper qqShareHelper;
    private ShareWechatHelper shareWechatHelper;
    private LinearLayout shareQQ;
    private LinearLayout shareQQZone;
    private LinearLayout changeTextLayout;

    private ChangeArticleDetalSizePop changeArticleDetalSizePop;

    public SharePop(Activity context) {
        this.context = context;
        qqShareHelper = new QQShareHelper(context);
        shareWechatHelper = new ShareWechatHelper(context);

        changeArticleDetalSizePop = new ChangeArticleDetalSizePop(context);

        View contentView = LayoutInflater.from(context).inflate(R.layout.share_pop, null);
        LinearLayout shareWechat = contentView.findViewById(R.id.share_wechat_layout);
        shareWechat.setOnClickListener(this);
        LinearLayout shareFriend = contentView.findViewById(R.id.share_wechat_friend);
        shareFriend.setOnClickListener(this);


        changeTextLayout = contentView.findViewById(R.id.change_text_layout);
        changeTextLayout.setOnClickListener(this);

        shareQQ = contentView.findViewById(R.id.share_qq);
        shareQQ.setOnClickListener(this);
        shareQQZone = contentView.findViewById(R.id.share_qq_zone);
        shareQQZone.setOnClickListener(this);

        LinearLayout shareQrCode = contentView.findViewById(R.id.share_qr_code);
        shareQrCode.setOnClickListener(this);
        LinearLayout shareLink = contentView.findViewById(R.id.share_link);
        shareLink.setOnClickListener(this);

        bottomShareLayout = contentView.findViewById(R.id.bottom_share_layout);

        TextView cancel = contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //popupWindow.setContentView(contentView);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                final WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);
            }
        });
    }



    public boolean isShow() {
        if (popupWindow == null) return false;
        return popupWindow.isShowing();
    }


    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }


    private boolean isArticle = false;

    private View root;
    private String aid;

    public void showTwo(View root, String aid) {
        this.root = root;
        this.isArticle = true;
        this.aid = aid;

        changeTextLayout.setVisibility(View.GONE);
        shareQQ.setVisibility(View.GONE);
        shareQQZone.setVisibility(View.GONE);
        bottomShareLayout.setVisibility(View.GONE);
        popupWindow.setAnimationStyle(R.style.Share_pop_anim);
        popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }

    public void showThree(View root, String aid) {
        this.root = root;
        this.isArticle = true;
        this.aid = aid;

        changeTextLayout.setVisibility(View.VISIBLE);
        shareQQ.setVisibility(View.GONE);
        shareQQZone.setVisibility(View.GONE);
        bottomShareLayout.setVisibility(View.GONE);
        popupWindow.setAnimationStyle(R.style.Share_pop_anim);
        popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }

    public void showFour(View root) {
        this.root = root;
        this.isArticle = false;


        changeTextLayout.setVisibility(View.GONE);
        shareQQ.setVisibility(View.VISIBLE);
        shareQQZone.setVisibility(View.VISIBLE);
        bottomShareLayout.setVisibility(View.VISIBLE);
        popupWindow.setAnimationStyle(R.style.Share_pop_anim);
        popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }

    private void share(int id) {
        if (isArticle) {
            ArticleApiHelper.articleShare(aid, new MyCallBack
                    () {
                @Override
                public void onSuccessful(HttpResponse response) {
                    Gson gson = new Gson();
                    ShareVo shareVo = gson.fromJson(gson.toJson(response.getData()), ShareVo.class);
                    share(id, shareVo);

                }
            });
        } else {
            share(id, null);
        }

    }


    private void share(int id, ShareVo shareVo) {
        switch (id) {
            case R.id.change_text_layout:
                if (!changeArticleDetalSizePop.isShow()) {
                    changeArticleDetalSizePop.show(root);
                }
                dismiss();
                break;
            case R.id.share_wechat_layout:
                if (isArticle) {
                    ShareUtil.wbShare(false, shareVo.getTitle(), shareVo.getDesc(),
                            shareVo.getImg(), shareVo.getUrl());
                } else {
                    shareWechatHelper.shareBigImg(false);
                }
                dismiss();
                break;
            case R.id.share_wechat_friend:
                if (isArticle) {
                    ShareUtil.wbShare(true, shareVo.getTitle(), shareVo.getDesc(),
                            shareVo.getImg(), shareVo.getUrl());
                } else {
                    shareWechatHelper.shareBigImg(true);
                }
                dismiss();
                break;
            case R.id.share_qq:
                qqShareHelper.shareBigImg(true);
                dismiss();
                break;
            case R.id.share_qq_zone:
                qqShareHelper.shareBigImg(false);
                dismiss();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_qr_code:
                if (AppUtils.isSign()) {
                    Intent intent = new Intent(context, WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", UrlCons.SHOUTU_URL + PreUtils
                            .getString(AppConstants.SPKEY.TOKEN, ""));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    dismiss();
                } else {
                    AppUtils.goGign(context);
                }
                break;
            case R.id.share_link:
                UserApiHelper.apprenticeLink(new
                        MyCallBack() {
                            @Override
                            public void onSuccessful(HttpResponse response) {
                                Gson gson = new Gson();
                                try {
                                    JSONObject object = new JSONObject(gson.toJson(response
                                            .getData()));
                                    String url = object.getString("url");
                                    CommUtils.copyToClipboard(context, url);
                                    dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                share(v.getId());
                break;
        }
    }
}
