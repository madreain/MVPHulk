package com.madreain.hulk.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.madreain.hulk.R;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */

public class AnimUtils {

    //动画切换图标
    public static void changeImgAlpha(ImageView view, @DrawableRes int resId) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setImageResource(resId);
                AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
                alphaAnimation1.setDuration(200);
                view.startAnimation(alphaAnimation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //动画切换图标+背景
    public static void changeImgAlpha(ImageView view, @DrawableRes int resId, @DrawableRes int background) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setImageResource(resId);
                AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
                alphaAnimation1.setDuration(200);
                view.startAnimation(alphaAnimation1);
                view.setBackgroundResource(background);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static void errorInfoFadeIn(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_error_in);
            view.setAnimation(animation);
            animation.start();
        }
    }

    public static void errorInfoFadeOut(Context context, View view) {
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_error_out);
            view.setAnimation(animation);
            animation.start();
        }
    }

    public static void topFadeOut(Context context, View view) {
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_top_fade_out);
            view.setAnimation(animation);
            animation.start();
        }
    }

    public static void topFadeIn(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_top_fade_in);
            view.setAnimation(animation);
            animation.start();
        }
    }

    public static void bottomFadeIn(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_bottom_fade_in);
            view.setAnimation(animation);
            animation.start();
        }
    }

    public static void bottomFadeOut(Context context, View view) {
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hulk_bottom_fade_out);
            view.setAnimation(animation);
            animation.start();
        }
    }

}
