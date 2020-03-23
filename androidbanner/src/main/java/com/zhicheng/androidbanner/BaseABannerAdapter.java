package com.zhicheng.androidbanner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicheng.androidbanner.adapter.ABannerAdapter;

/**
 * Project: AndroidBanner
 * ClassName: BaseAbannerAdapter
 * Date: 2020/3/19 08:58
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is BaseAbannerAdapter description !
 */
public abstract class BaseABannerAdapter{

    interface IABannerAdapterCallback{
        void notifyDataSetChanged();
    }
    private IABannerAdapterCallback callback;

    public abstract int getCount();
    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }
    public abstract View getView(int position, int viewType, View reusedView, ViewGroup parent);
    public int getViewType(int position){
        return 0;
    }

    final public void notifyDataSetChanged(){
        if (callback != null) {
            callback.notifyDataSetChanged();
        }
    }
    final void setCallback(IABannerAdapterCallback callback){
        this.callback = callback;
    }
}
