package com.zhicheng.androidbanner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

/**
 * Project: AndroidBanner
 * ClassName: ActivityUtils
 * Date: 2020/3/23 09:56
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ActivityUtils description !
 */
public class ActivityUtils {

    public static Activity getActivity(View view){
        if (view == null) return null;
        Context context = view.getContext();
        if (context == null) return null;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
