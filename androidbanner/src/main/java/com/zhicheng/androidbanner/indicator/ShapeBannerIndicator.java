package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.androidbanner.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: AndroidBanner
 * ClassName: ShapeBannerIndicator
 * Date: 2020/3/19 20:40
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ShapeBannerIndicator description !
 */
public abstract class ShapeBannerIndicator extends BaseBannerIndicator {

    private int mNormalColor = Color.parseColor("#a5b1b4");
    private int mSelectColor = Color.parseColor("#4f8ef2");
    private ASize mNormalSize;
    private ASize mSelectSize;

    public ShapeBannerIndicator(Context context) {
        this(context,null);
    }

    public ShapeBannerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeBannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getIndicatorItemView(boolean selected) {
        View indicatorView = new View(mContext);
        if (selected){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mSelectSize.getWidth(), mSelectSize.getHeight());
            indicatorView.setLayoutParams(params);
            indicatorView.setBackground(getDrawable(mSelectColor));
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mNormalSize.getWidth(), mNormalSize.getHeight());
            indicatorView.setLayoutParams(params);
            indicatorView.setBackground(getDrawable(mNormalColor));
        }
        return indicatorView;
    }

    @Override
    public void updateIndicatorItemView(View view, boolean selected) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (selected){
            params.width = mSelectSize.getWidth();
            params.height = mSelectSize.getHeight();
            view.setBackground(getDrawable(mSelectColor));
        }else{
            params.width = mNormalSize.getWidth();
            params.height = mNormalSize.getHeight();
            view.setBackground(getDrawable(mNormalColor));
        }
        view.setLayoutParams(params);
    }

    protected void updateAnimationView(){
        RelativeLayout.LayoutParams params = (LayoutParams) mAnimationView.getLayoutParams();
        if (params == null){
            params = new RelativeLayout.LayoutParams(mSelectSize.getWidth(), mSelectSize.getHeight());
        }else{
            params.width = mSelectSize.getWidth();
            params.height = mSelectSize.getHeight();
        }
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        mAnimationView.setLayoutParams(params);
        mAnimationView.setBackground(getDrawable(mSelectColor));
    }

    public abstract Drawable getDrawable(int color);

    public void setNormalSize(ASize normalSize) {
        this.mNormalSize = normalSize;
    }

    public void setSelectSize(ASize selectSize) {
        this.mSelectSize = selectSize;
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public void setSelectColor(int selectColor) {
        this.mSelectColor = selectColor;
    }

}
