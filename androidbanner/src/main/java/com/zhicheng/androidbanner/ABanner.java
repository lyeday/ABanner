package com.zhicheng.androidbanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.zhicheng.androidbanner.adapter.ABannerAdapter;
import com.zhicheng.androidbanner.indicator.BaseBannerIndicator;
import com.zhicheng.androidbanner.indicator.ImageBannerIndicator;
import com.zhicheng.androidbanner.indicator.RectBannerIndicator;
import com.zhicheng.androidbanner.indicator.RoundRectBannerIndicator;
import com.zhicheng.androidbanner.indicator.CircleBannerIndicator;
import com.zhicheng.androidbanner.listener.ABannerItemClickListener;
import com.zhicheng.androidbanner.transformer.BaseBannerTransformer;
import com.zhicheng.androidbanner.transformer.GalleryTransformer;
import com.zhicheng.androidbanner.view.BannerViewPager;

import java.util.IllegalFormatCodePointException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Project: AndroidBanner
 * ClassName: ABanner
 * Date: 2020/3/19 08:28
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ABanner description !
 */
public class ABanner extends FrameLayout {
    private static final String TAG = "ABanner";

    public enum BannerType {
        //经典模式
        CLASSICS,
        //画廊模式
        GALLERY,
    }

    public enum IndicatorType{
        NONE,
        CIRCLE,
        RECTANGLE,
        ROUND_RECTANGLE,
        IMAGE,
        CUSTOM,
    }

    /**
     * 指示器位置
     */
    public enum IndicatorPosition{
        CENTER_BOTTOM,
        RIGHT_BOTTOM,
        LEFT_BOTTOM,
    }

    private final static int MSG_WHAT_BANNER_LOOP = 100;

    private BannerViewPager mViewPager;

    private Context mContext;
    private BaseABannerAdapter mAdapter;
    private ABannerAdapter mViewPagerAdapter;
    private Timer mTimer;
    private int mLoopDuration;
    private int mCurrentPosition = 1;
    private BannerType mBannerType;
    private boolean isAutoLoop = false;
    private BaseBannerTransformer mBannerTransformer;
    private IndicatorType mIndicatorType;
    private IndicatorPosition mIndicatorPosition;
    private BaseBannerIndicator mBannerIndicator;
    private RelativeLayout mRelativeLayout;
    private int mPageMargin = 30;
    private int mPageSide = 80;
    private float mScale = 0.7f;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MSG_WHAT_BANNER_LOOP:
                    if (mAdapter == null || mAdapter.getCount() <= 1) return;
                    mCurrentPosition++;
                    mViewPager.setCurrentItem(mCurrentPosition,true);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (onPageChangeListener != null){
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosition = position;
            if (onPageChangeListener != null){
                onPageChangeListener.onPageSelected(getCurrentPosition());
            }
            if (mBannerIndicator != null){
                mBannerIndicator.setCurrentPosition(getCurrentPosition());
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener!=null){
                onPageChangeListener.onPageScrollStateChanged(state);
            }
            switch (state){
                case ViewPager.SCROLL_STATE_DRAGGING:
                    if (isAutoLoop) {
                        stopLoop(true);
                    }
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (isAutoLoop) {
                        startLoop(mLoopDuration);
                    }
                    break;
            }
        }

    };

    public ABanner(@NonNull Context context) {
        this(context,null);
    }

    public ABanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ABanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialization();
    }

    private void initialization() {
        mBannerType = BannerType.CLASSICS;
        mViewPager = new BannerViewPager(mContext);
        FrameLayout.LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        if (layoutParams == null){
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setOnPageChangeListener(mPageChangeListener);
        mViewPager.addOnPageChangeListener(mPageChangeListener);

        mRelativeLayout = new RelativeLayout(mContext);
        LayoutParams mRelativeLayoutLayout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRelativeLayout.setLayoutParams(mRelativeLayoutLayout);

        this.addView(mViewPager);
        this.addView(mRelativeLayout);
    }

    private void stopLoop(boolean autoLoop){
        this.isAutoLoop = autoLoop;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 开始自动轮播
     * @param delayms 毫秒
     */
    public void startLoop(int delayms){
        mLoopDuration = delayms;
        isAutoLoop = true;
        if (mTimer != null){
            return;
        }
        mTimer = new Timer(getClass().getName());
        mTimer.schedule(new BannerLoopTask(),delayms,delayms);
    }

    /**
     * 停止轮播
     */
    public void stop(){
        stopLoop(false);
    }

    public BaseABannerAdapter getAdapter() {
        return mAdapter;
    }

    public ABanner setAdapter(BaseABannerAdapter adapter) {
        this.mAdapter = adapter;
        if (adapter == null){
            throw new IllegalArgumentException("Banner Adapter not be null !");
        }
        mViewPagerAdapter = new ABannerAdapter(adapter);
        mViewPager.setAdapter(mViewPagerAdapter);
        adapter.setCallback(new BaseABannerAdapter.IABannerAdapterCallback() {
            @Override
            public void notifyDataSetChanged() {
                initStartItem();
                mViewPagerAdapter.notifyDataSetChanged();
            }
        });
        initStartItem();
//        if (mAdapter.getCount() > 1){
//            mCurrentPosition = 1;
//        }else {
//            mCurrentPosition = 0;
//        }
        return this;
    }

    public void initStartItem(){
        if (mAdapter == null || mAdapter.getCount() == 0) return;
        mCurrentPosition = Integer.MAX_VALUE/2;
        mCurrentPosition -= mCurrentPosition % mAdapter.getCount();
        mViewPager.setCurrentItem(mCurrentPosition,false);
        if (mBannerIndicator!=null){
            mBannerIndicator.setNumPages(mAdapter.getCount());
        }
    }

    /**
     * 设置轮播控件间距
     * @param marginPixels marginPixels
     * @return
     */
    public ABanner setPageMargin(int marginPixels){
        mViewPager.setPageMargin(marginPixels);
        this.mPageMargin = marginPixels;
        return this;
    }

    /**
     * 设置额外显示的轮播控件大小
     * @param sidePixels sidePixels
     * @return
     */
    public ABanner setPageSide(int sidePixels){
        this.mPageSide = sidePixels;
        mViewPager.setPadding(sidePixels,0,sidePixels,0);
        mViewPager.setClipToPadding(false);
        return this;
    }

    /**
     * 设置轮播图变换方式
     * @param type
     * @return
     */
    public ABanner setBannerTransformerType(BannerType type){
        this.mBannerType = type;
        switch (type){
            case GALLERY:
            {
                if (mViewPager.getPageMargin() == 0 &&
                        mPageMargin != 0) {
                    mViewPager.setPageMargin(mPageMargin);
                }
                if (mViewPager.getPaddingLeft() != mPageSide){
                    setPageSide(mPageSide);
                }
                mBannerTransformer = new GalleryTransformer();
            }
                break;
        }
        if (mBannerTransformer != null){
            mBannerTransformer.setScale(mScale);
            mViewPager.setPageTransformer(true,mBannerTransformer);
        }
        return this;
    }

    /**
     * 设置自定义的动画类型
     * @param bannerTransformer
     * @return
     */
    public ABanner setCustomTransformer(BaseBannerTransformer bannerTransformer){
        if (bannerTransformer != null){
            mBannerTransformer = bannerTransformer;
            mBannerTransformer.setScale(mScale);
            mViewPager.setPageTransformer(true,mBannerTransformer);
        }
        return this;
    }

    /**
     * 设置轮播图自动滚动动画间隔
     * @param duration 毫秒级时间
     * @return
     */
    public ABanner setBannerTransformerDuration(int duration){
        mViewPager.setAnimationDuration(duration);
        return this;
    }

    /**
     * ! 只对 `GALLERY` 有效, 设置临近图片显示
     * @param scale 缩放 在0.1 ~ 1.0 之间 1.0 不缩放
     * @return
     */
    public ABanner setBannerTrannerScale(float scale){
        if (scale > 1.0f){
            scale = 1.0f;
        }else if (scale < 0.1f){
            scale = 0.1f;
        }
        mScale = scale;
        if (mBannerTransformer!=null){
            mBannerTransformer.setScale(mScale);
        }
        return this;
    }

    /**
     * 设置点击的监听
     * @param onItemClickListener ABannerItemClickListener
     */
    public void setOnItemClickListener(ABannerItemClickListener onItemClickListener) {
        mViewPagerAdapter.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 设置指示器类型
     * @param indicatorType
     * @return
     */
    public ABanner setIndicatorType(IndicatorType indicatorType) {
        this.mIndicatorType = indicatorType;
        if (indicatorType != IndicatorType.CUSTOM && mBannerIndicator != null){
            mRelativeLayout.removeView(mBannerIndicator);
        }
        switch (indicatorType){
            case NONE:
            case CUSTOM:
                break;
            case CIRCLE:
                mBannerIndicator = new CircleBannerIndicator(mContext);
                break;
            case RECTANGLE:
                mBannerIndicator = new RectBannerIndicator(mContext);
                break;
            case ROUND_RECTANGLE:
                mBannerIndicator = new RoundRectBannerIndicator(mContext);
                break;
            case IMAGE:
                mBannerIndicator = new ImageBannerIndicator(mContext);
                break;
        }
        if (mBannerIndicator != null) {
            mBannerIndicator.setNumPages(mAdapter.getCount());
            mBannerIndicator.setCurrentPosition(getCurrentPosition());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mBannerIndicator.setLayoutParams(layoutParams);
            if (mBannerIndicator.getParent() == null) {
                mRelativeLayout.addView(mBannerIndicator);
            }
        }
        return this;
    }

    public ABanner setCustomIndicator(BaseBannerIndicator bannerIndicator){
        if (mBannerIndicator != null && mBannerIndicator.getParent() != null){
            mRelativeLayout.removeView(mBannerIndicator);
        }
        return setIndicatorType(IndicatorType.CUSTOM);
    }

    public ABanner setIndicatorPosition(IndicatorPosition indicatorPosition, Rect margin) {
            this.mIndicatorPosition = indicatorPosition;
            if (mBannerIndicator == null){
                throw new IllegalArgumentException("请先调用 `setIndicatorType`/`setCustomIndicator`方法初始化 BannerIndicator !");
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBannerIndicator.getLayoutParams();
            switch (indicatorPosition){
                case LEFT_BOTTOM:
                    layoutParams.bottomMargin = margin.bottom;
                    layoutParams.leftMargin = margin.left;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                    break;
                case RIGHT_BOTTOM:
                    layoutParams.bottomMargin = margin.bottom;
                    layoutParams.rightMargin = margin.left;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                    break;
                case CENTER_BOTTOM:
                    layoutParams.bottomMargin = margin.bottom;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
                    break;
                default:
                    break;
            }
            mBannerIndicator.setLayoutParams(layoutParams);
        return this;
    }

    private class BannerLoopTask extends TimerTask{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(MSG_WHAT_BANNER_LOOP);
        }
    }

    public int getCurrentPosition() {
        return mCurrentPosition%mAdapter.getCount();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mBannerIndicator != null) {
            mBannerIndicator.setCurrentPosition(getCurrentPosition());
        }
    }

    /**
     * 获取指示器 可以设置样式
     * @return 当前的指示器 或 null
     */
    public BaseBannerIndicator getBannerIndicator() {
        return mBannerIndicator;
    }


    private ViewPager.OnPageChangeListener onPageChangeListener;
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (isAutoLoop) {
            if (hasWindowFocus) {
                startLoop(mLoopDuration);
            } else {
                stopLoop(true);
            }
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }
}
