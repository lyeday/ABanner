package com.zhicheng.androidbanner.utils;

import android.content.Context;

/**
 * Project: AndroidBanner
 * ClassName: DisplayUtils
 * Date: 2020/3/19 20:47
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is DisplayUtils description !
 */
public class DisplayUtils {
    public static int dip2px(Context context, int dip){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int px2dip(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
