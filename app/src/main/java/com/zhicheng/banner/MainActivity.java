package com.zhicheng.banner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zhicheng.androidbanner.ABanner;
import com.zhicheng.androidbanner.BaseABannerAdapter;
import com.zhicheng.androidbanner.indicator.BaseBannerIndicator;
import com.zhicheng.androidbanner.indicator.ImageBannerIndicator;
import com.zhicheng.androidbanner.indicator.ShapeBannerIndicator;
import com.zhicheng.androidbanner.listener.ABannerItemClickListener;
import com.zhicheng.androidbanner.utils.ASize;
import com.zhicheng.banner.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private List<String> list = new ArrayList<>();

    private ABanner mBanner;
    private ABanner mBanner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBanner = findViewById(R.id.banner);
        mBanner2 = findViewById(R.id.banner2);
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892242&di=41b0d6ff827f7db06317fd5eab72b7bb&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F6a63f6246b600c334c3e91cb1e4c510fd9f9a16a.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892242&di=536f1ba0de8c99c1eea0ae8f78b5e1f5&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fb%2F55597435bb036.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892242&di=3a148739b4da446b8c449e1ab80aad6b&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201111%2F21%2F205700txzuacubbcy91u99.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892242&di=3e47683685bc6beadaa6bf107f03aa85&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201408%2F07%2F194456i55q58pqnb55fi88.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892242&di=f9471e0b5c739ecc42606eb462393cda&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201401%2F11%2F145825zn1sxa8anrg11gt1.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892241&di=6ea521956c300f93b5a013514cf3d0b3&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Feac4b74543a98226c4f2ef048982b9014a90ebe6.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892241&di=91013092750ec3728deecc316dc49e61&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fdesk%2Fbizhi%2Fimage%2F3%2F1680x1050%2F1375753300484.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892241&di=3c4cebf8fbbb8235fe2de51a2a93e134&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201407%2F12%2F191515sskuppax42aarano.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584626892240&di=fad86e2f36eb2e2393559f8dce44a860&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201307%2F19%2F092658ukvx5evhkvhkl4vl.jpg");
        initBannerClassics();
        initBannerGallery();
    }

    private void initBannerGallery() {
        mBanner2.setBannerTransformerType(ABanner.BannerType.GALLERY)
                .setAdapter(new BannerAdapter(true))
                .setBannerTransformerDuration(1000)
                .setBannerTrannerScale(0.8f)
                .setIndicatorType(ABanner.IndicatorType.IMAGE)
                .setIndicatorPosition(ABanner.IndicatorPosition.CENTER_BOTTOM,new Rect(80,50,80,20))
                .startLoop(3000);

        mBanner2.setOnItemClickListener(new ABannerItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                showToast(position+" 画廊");
            }
        });
    }

    private void initBannerClassics() {
        mBanner.setBannerTransformerType(ABanner.BannerType.CLASSICS)
                .setAdapter(new BannerAdapter(false))
                .setBannerTransformerDuration(1000)
                .setIndicatorType(ABanner.IndicatorType.ROUND_RECTANGLE)
                .setIndicatorPosition(ABanner.IndicatorPosition.RIGHT_BOTTOM,new Rect(80,50,80,20))
                .startLoop(2500);
        ShapeBannerIndicator bannerIndicator = (ShapeBannerIndicator) mBanner.getBannerIndicator();
        bannerIndicator.setSelectColor(Color.RED);
        bannerIndicator.setNormalColor(Color.LTGRAY);
        bannerIndicator.reset();
        mBanner.setOnItemClickListener(new ABannerItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                showToast(position+" 经典");
            }
        });
    }


    private class BannerAdapter extends BaseABannerAdapter{

        private boolean round = false;

        public BannerAdapter(boolean round) {
            this.round = round;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, int viewType, View reusedView, ViewGroup parent) {
            View view = reusedView;
            if (view == null){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_banner, parent, false);
            }
            ImageView imageView = view.findViewById(R.id.iv_image);
            RoundedCorners roundedCorners = new RoundedCorners(20);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            if (round) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(MainActivity.this)
                        .load(list.get(position % list.size()))
                    .apply(options)
                        .into(imageView);
            }else{
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MainActivity.this)
                        .load(list.get(position % list.size()))
                        .into(imageView);
            }
            return view;
        }
    }



    private Toast toast;
    private void showToast(String msg){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this,"",Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

}
