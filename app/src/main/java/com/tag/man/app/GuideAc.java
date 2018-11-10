package com.tag.man.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tag.man.BuildConfig;
import com.tag.man.R;
import com.tag.man.app.main.MainAc;
import com.tag.man.mylibrary.comm.PreUtils;

import java.util.ArrayList;

public class GuideAc extends Activity {

    private ArrayList<ImageView> imgs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        PreUtils.saveBool(BuildConfig.VERSION_NAME, true);

        ViewPager viewPager = findViewById(R.id.guide_vp);

        ImageView imgOne = new ImageView(this);
        imgOne.setScaleType(ImageView.ScaleType.FIT_XY);

        imgs.add(imgOne);


        ImageView imgTwo = new ImageView(this);
        imgTwo.setScaleType(ImageView.ScaleType.FIT_XY);
        imgs.add(imgTwo);


        ImageView imgThree = new ImageView(this);
        imgThree.setScaleType(ImageView.ScaleType.FIT_XY);
        imgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
        imgs.add(imgThree);

        GuideAdapter adapter = new GuideAdapter();

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private boolean isLastPage = false;
            private boolean isDragPage = false;
            private boolean canJumpPage = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage) {
                        canJumpPage = false;
                        goNext();
                    }

                }
            }
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == imgs.size()-1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                isDragPage = state == 1;
            }
        });

    }



    private void goNext() {
        startActivity(new Intent(this, MainAc.class));
        finish();
    }



    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imgs.get(position));
            return imgs.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

    }

}
