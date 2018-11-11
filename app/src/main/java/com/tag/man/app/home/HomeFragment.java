package com.tag.man.app.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tag.man.R;
import com.tag.man.app.home.activity.MusicActivity;
import com.tag.man.app.home.activity.NewsActivity;
import com.tag.man.app.home.activity.StarActivity;
import com.tag.man.app.home.activity.VideoActivity;
import com.tag.man.base.BaseFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.home_banner)
    Banner home_banner;

    @BindView(R.id.news_layout)
    LinearLayout news_layout;
    @BindView(R.id.music_layout)
    LinearLayout music_layout;
    @BindView(R.id.video_layout)
    LinearLayout video_layout;
    @BindView(R.id.star_layout)
    LinearLayout star_layout;


    @BindView(R.id.home_recommend_ry)
    RecyclerView home_recommend_ry;


    ArrayList<String> bannerUrls = new ArrayList<>();


    @Override
    public void loadData() {
        bannerUrls.add("https://goss.vcg.com/creative/vcg/800/version23/VCG41155380802.jpg");
        bannerUrls.add("https://goss.vcg.com/creative/vcg/800/version23/VCG41155380802.jpg");
        bannerUrls.add("https://goss.vcg.com/creative/vcg/800/version23/VCG41155380802.jpg");
        bannerUrls.add("https://goss.vcg.com/creative/vcg/800/version23/VCG41155380802.jpg");

        home_banner.setImages(bannerUrls);

    }


    @OnClick({
            R.id.news_layout,
            R.id.music_layout,
            R.id.video_layout,
            R.id.star_layout
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_layout:
                jump(NewsActivity.class);
                break;

            case R.id.music_layout:
                jump(MusicActivity.class);
                break;

            case R.id.video_layout:
                jump(VideoActivity.class);
                break;

            case R.id.star_layout:
                jump(StarActivity.class);
                break;
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }
}
