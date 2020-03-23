package com.zhicheng.androidbanner.indicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.androidbanner.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: AndroidBanner
 * ClassName: BaseBannerIndicator
 * Date: 2020/3/20 19:35
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is BaseBannerIndicator description !
 */
public abstract class BaseBannerIndicator extends RelativeLayout {
    private final String TAG = this.getClass().getSimpleName();

    final private List<View> mChildList = new ArrayList<>();
    protected int mNumPages;
    protected int mCurrentPosition = 0;
    protected Context mContext;
    protected Rect mMargin;
    protected View mAnimationView;
    protected LinearLayout mContentLayout;
    protected int mAnimationDuration = 300;

    public BaseBannerIndicator(Context context) {
        this(context,null);
    }

    public BaseBannerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseBannerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initParams();
        initViews();
    }

    private void initViews() {
        mAnimationView = getIndicatorItemView(true);
        mContentLayout = new LinearLayout(mContext);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);
        mContentLayout.setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        mContentLayout.setGravity(Gravity.CENTER);
        addView(mContentLayout);
        addView(mAnimationView);

        RelativeLayout.LayoutParams params = (LayoutParams) mAnimationView.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        mAnimationView.setLayoutParams(params);
    }


    public void setNumPages(int numPages) {
        this.mNumPages = numPages;
        reset();
    }

    public void reset(){
        updateIndicatorItemView(mAnimationView,true);
        while (mChildList.size()<mNumPages && mChildList.size() > 0){
            View view = mChildList.get(0);
            if (view != null) {
                mChildList.remove(view);
                mContentLayout.removeView(view);
            }
        }
        for (int i = 0; i < mNumPages; i++) {
            View indicatorView;
            if (mChildList.size() > i){
                indicatorView = mChildList.get(i);
                updateIndicatorItemView(indicatorView,false);
            }else{
                indicatorView = getIndicatorItemView(false);
                mChildList.add(indicatorView);
                mContentLayout.addView(indicatorView);
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicatorView.getLayoutParams();
            params.setMargins(mMargin.left,mMargin.top,mMargin.right,mMargin.bottom);
            indicatorView.setLayoutParams(params);
        }
    }

    public void setCurrentPosition(int currentPosition) {
        if (mMargin.left + mAnimationView.getWidth() + mMargin.right < mAnimationView.getWidth()){
            throw new IllegalArgumentException("指示器的 margin.left + margin.right + normal.size.width 必须大于 select.size.width !");
        }
        startAnimation(mCurrentPosition,currentPosition);
        this.mCurrentPosition = currentPosition;
    }

    private void startAnimation(final int from, final int to){
        if (from == to) return;
        if (from < mChildList.size() && to < mChildList.size()) {
            final View fromView = mChildList.get(from);
            final View toView = mChildList.get(to);
            float offset_x = (mAnimationView.getWidth() - fromView.getWidth())/2.0f;
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    fromView.getX() - mAnimationView.getLeft() - offset_x,
                    toView.getX() - mAnimationView.getLeft() - offset_x,
                    0,
                    0);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    fromView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    toView.setVisibility(INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            translateAnimation.setRepeatCount(0);
            translateAnimation.setDuration(mAnimationDuration);
            translateAnimation.setFillAfter(true);
            mAnimationView.startAnimation(translateAnimation);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!changed) return;
        if (mChildList.size() > mCurrentPosition && mAnimationView != null) {
            View view = mChildList.get(mCurrentPosition);
            int left = (int) (view.getX() - (mAnimationView.getWidth() - view.getWidth()) / 2.0f + 0.5f - mAnimationView.getLeft());
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) mAnimationView.getLayoutParams();
            layoutParams.setMargins(left, mMargin.top - (mAnimationView.getHeight() - view.getHeight()) / 2, 0, 0);
            mAnimationView.setLayoutParams(layoutParams);
        }
    }

    public void initParams(){
        int px = DisplayUtils.dip2px(mContext, 4);
        mMargin = new Rect(px,px,px,px);
    }
    public abstract void updateIndicatorItemView(View view,boolean selected);
    public abstract View getIndicatorItemView(boolean selected);
    public void setAnimationDuration(int mAnimationDuration) {
        this.mAnimationDuration = mAnimationDuration;
    }
}
