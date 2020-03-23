package com.zhicheng.androidbanner.transformer;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Project: AndroidBanner
 * ClassName: GalleryTransformer
 * Date: 2020/3/19 15:49
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is GalleryTransformer description !
 */
public class GalleryTransformer extends BaseBannerTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        float finalScale = getScale();
        if (position >= 0 && position <= 1) {
            finalScale = getScale() + (1 - getScale()) * (1 - position);
        } else if (position > -1 && position < 0) {
            finalScale = 1 + (1 - getScale()) * position;
        }
        page.setScaleY(finalScale);
        page.postInvalidate();
    }
}
