package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.androidbanner.utils.DisplayUtils;

/**
 * Project: AndroidBanner
 * ClassName: RectBannerIndicator
 * Date: 2020/3/20 15:51
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is RectBannerIndicator description !
 */
public class RectBannerIndicator extends ShapeBannerIndicator {

    public RectBannerIndicator(Context context) {
        super(context);
    }

    public RectBannerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectBannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initParams() {
        super.initParams();
        ASize normalSize = new ASize(DisplayUtils.dip2px(mContext,5),DisplayUtils.dip2px(mContext,5));
        ASize selectSize = new ASize(DisplayUtils.dip2px(mContext,8),DisplayUtils.dip2px(mContext,5));
        setNormalSize(normalSize);
        setSelectSize(selectSize);
    }

    @Override
    public Drawable getDrawable(int color) {
        Shape s = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(s);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
}
