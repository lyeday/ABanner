package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.androidbanner.utils.DisplayUtils;

/**
 * Project: AndroidBanner
 * ClassName: RoundRectBannerIndicator
 * Date: 2020/3/20 18:59
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is RoundRectBannerIndicator description !
 */
public class RoundRectBannerIndicator extends ShapeBannerIndicator {
    private ASize normalSize;
    public RoundRectBannerIndicator(Context context) {
        super(context);
    }

    public RoundRectBannerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRectBannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initParams() {
        super.initParams();
        ASize normalSize = new ASize(DisplayUtils.dip2px(mContext,6),DisplayUtils.dip2px(mContext,6));
        ASize selectSize = new ASize(DisplayUtils.dip2px(mContext,13),DisplayUtils.dip2px(mContext,6));
        this.normalSize = normalSize;
        setNormalSize(normalSize);
        setSelectSize(selectSize);
    }

    @Override
    public Drawable getDrawable(int color) {
        int radii = normalSize.getHeight();
        float[] outerRadii = new float[]{radii,radii,radii,radii,radii,radii,radii,radii};
        Shape s = new RoundRectShape(outerRadii,null,null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(s);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
}
