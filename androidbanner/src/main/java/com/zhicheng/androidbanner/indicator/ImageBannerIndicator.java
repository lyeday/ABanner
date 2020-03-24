package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zhicheng.androidbanner.R;

/**
 * Project: AndroidBanner
 * ClassName: ImageBannerIndicator
 * Date: 2020/3/23 11:08
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ImageBannerIndicator description !
 */
public class ImageBannerIndicator extends BaseBannerIndicator {

    private Drawable mSelectDrawable;
    private Drawable mNormalDrawable;

    public ImageBannerIndicator(Context context) {
        super(context);
    }

    public ImageBannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBannerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initParams() {
        super.initParams();
        mSelectDrawable = getResources().getDrawable(R.drawable.ic_banner_default_select);
        mNormalDrawable = getResources().getDrawable(R.drawable.ic_banner_default_normal);
        mSelectDrawable.setBounds(0,0,mSelectDrawable.getIntrinsicWidth(),mSelectDrawable.getIntrinsicHeight());
        mNormalDrawable.setBounds(0,0,mNormalDrawable.getIntrinsicWidth(),mNormalDrawable.getIntrinsicHeight());
    }

    @Override
    public void updateIndicatorItemView(@NonNull View view, boolean selected) {
        ImageView imageView = (ImageView) view;
        if (selected){
            imageView.setImageDrawable(mSelectDrawable);
        }else{
            imageView.setImageDrawable(mNormalDrawable);
        }
    }

    @Override
    public View getIndicatorItemView(boolean selected) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (selected){
            imageView.setImageDrawable(mSelectDrawable);
        }else{
            imageView.setImageDrawable(mNormalDrawable);
        }
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    public void setSelectDrawable(Drawable selectDrawable) {
        this.mSelectDrawable = mSelectDrawable;
    }

    public void setNormalDrawable(Drawable normalDrawable) {
        this.mNormalDrawable = mNormalDrawable;
    }
}
