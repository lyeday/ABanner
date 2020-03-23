package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.androidbanner.utils.DisplayUtils;

/**
 * Project: AndroidBanner
 * ClassName: CircleBannerIndicator
 * Date: 2020/3/19 20:37
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is CircleBannerIndicator description !
 */
public class CircleBannerIndicator extends ShapeBannerIndicator {

    public CircleBannerIndicator(Context context) {
        super(context);
    }

    public CircleBannerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleBannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initParams() {
        super.initParams();
        ASize aSize = new ASize(DisplayUtils.dip2px(mContext, 6), DisplayUtils.dip2px(mContext, 6));
        setNormalSize(aSize);
        setSelectSize(aSize.copy());
    }

    public Drawable getDrawable(int color){
        Shape s = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(s);
        drawable.getPaint().setColor(color);
        drawable.setBounds(0,0,0,0);
        return drawable;
    }
}
