package com.zhicheng.androidbanner.utils;

/**
 * Project: AndroidBanner
 * ClassName: ASize
 * Date: 2020/3/20 15:52
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ASize description !
 */
public class ASize {
    private int width;
    private int height;

    public ASize(){}
    public ASize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ASize copy() {
        return new ASize(getWidth(),getHeight());
    }
}
