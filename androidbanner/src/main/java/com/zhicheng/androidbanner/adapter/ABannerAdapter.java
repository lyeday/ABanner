package com.zhicheng.androidbanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zhicheng.androidbanner.BaseABannerAdapter;
import com.zhicheng.androidbanner.listener.ABannerItemClickListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Project: AndroidBanner
 * ClassName: ABannerAdapter
 * Date: 2020/3/19 08:47
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ABannerAdapter description !
 */
public class ABannerAdapter extends PagerAdapter {
    private String TAG = "ABannerAdapter";
    private final static int CACHE_MAX = 16;
    private BaseABannerAdapter mIAdapter;
    private ABannerItemClickListener onItemClickListener;

    public void setOnItemClickListener(ABannerItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //缓存机制
    private Map<Integer, Vector<Object>> mRecycleCache = Collections.synchronizedMap(new HashMap<Integer, Vector<Object>>());

    public ABannerAdapter(BaseABannerAdapter mIAdapter) {
        this.mIAdapter = mIAdapter;
    }

    @Override
    public int getCount() {
        if (mIAdapter.getCount() > 1) {
            return Integer.MAX_VALUE;
        }
        return mIAdapter.getCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private int getViewType(int position) {
        int finalPostion = getFinalPostion(position);
        return mIAdapter.getViewType(finalPostion);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final int finalPosition = getFinalPostion(position);
        ;
        View view = null;
        int viewType = getViewType(finalPosition);
        if (mRecycleCache.containsKey(viewType)) {
            Vector<Object> objectVector = mRecycleCache.get(viewType);
            if (!objectVector.isEmpty()) {
                Object element = objectVector.firstElement();
                objectVector.remove(element);
                view = (View) element;
            }
        }
        view = mIAdapter.getView(finalPosition, viewType, view, container);
        if (view.getParent() == null) {
            container.addView(view);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, finalPosition);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        view.setOnClickListener(null);
        container.removeView(view);
        Vector<Object> list;
        int finalPostion = getFinalPostion(position);
        int viewType = getViewType(finalPostion);
        if (mRecycleCache.containsKey(viewType)) {
            list = mRecycleCache.get(viewType);
        } else {
            list = new Vector<>(CACHE_MAX);
        }
        list.add(object);
        if (list.size() > CACHE_MAX) {
            list.remove(0);
        }
        mRecycleCache.put(viewType, list);
    }

    private int getFinalPostion(int position) {
        int finalPosition = position % mIAdapter.getCount();
//        int realCount = mIAdapter.getCount();
//        if (realCount <= 1){
//            return position;
//        }
//        if (position == 0){
//            finalPosition = realCount - 1;
//        }else if (position == realCount + 1){
//            finalPosition = 0;
//        }else{
//            finalPosition = position - 1;
//        }
        return finalPosition;
    }
}
