package com.zhicheng.androidbanner.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Project: AndroidBanner
 * ClassName: BaseBannerTransformer
 * Date: 2020/3/19 19:29
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is BaseBannerTransformer description !
 */
public abstract class BaseBannerTransformer implements ViewPager.PageTransformer {

    private final String TAG = getClass().getSimpleName();

    private float mScale = 0.8f;
    public void setScale(float Scale) {
        this.mScale = Scale;
    }

    public float getScale() {
        return mScale;
    }
}
