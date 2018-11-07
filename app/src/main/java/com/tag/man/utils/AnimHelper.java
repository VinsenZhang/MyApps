package com.tag.man.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 动画工具类
 * Created by vinse on 2018/8/30.
 */

public class AnimHelper {


    public static void FlipAnimatorXViewShow(View target, final long time) {
        if (target == null) return;
        target.clearAnimation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotationY", 0, 360);
        animator.setDuration(time).start();
    }


    public static void flipFromRight(View target, long duration) {
        if (target == null) return;
        target.clearAnimation();
        ViewGroup.LayoutParams params = target.getLayoutParams();
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationX", 0f, -params.width);
        animation.setDuration(duration).start();
    }


    public static void flipRight(View target, long duration) {
        if (target == null) return;
        target.clearAnimation();
        ViewGroup.LayoutParams params = target.getLayoutParams();
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationX", -params.width, 0f);
        animation.setDuration(duration).start();
    }


    public static void moveUpDownForever(View target) {
        if (target == null) return;
        target.clearAnimation();
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationY", -10f, 10f);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setDuration(500).start();
    }


    public static void moveUp(final View target, final View otherView, long duration) {
        if (target == null || otherView == null) return;
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationY", 50f, -20f);
        animation.setDuration(duration).start();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if (target != null) {
//                    target.setVisibility(View.GONE);
//                }
//
//                if (otherView != null) {
//                    otherView.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public static void moveDown(final View target, final View otherView, long duration) {
        if (target == null || otherView == null) return;
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationY", -100f, 100f);
        animation.setDuration(duration).start();

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if (target != null) {
//                    target.setVisibility(View.GONE);
//                }
//
//                if (otherView != null) {
//                    otherView.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


}
