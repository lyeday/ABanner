# ABanner
ABanner 是一个安卓的轮播图，支持画廊效果，支持自定义各种效果，支持自定义指示器

[ ![Download](https://api.bintray.com/packages/zhichengwu/ABanner/ABanner/images/download.svg) ](https://bintray.com/zhichengwu/ABanner/ABanner/_latestVersion)

# 效果图

![效果图](image.png)

# 特点
 - 无限轮播
 - 经典效果
 - 画廊效果
 - 多种指示器（圆角矩形，矩形，圆点，图片等模式）配置
 - 形状指示器支持设置选中与未选择颜色
 - 自定义效果
 - 自定义指示器
 - 等等....

# 用法

1、 在`project`中的`gradle`的`allprojects`中添加
 
````
allprojects {
    repositories {
        google()
        jcenter()
        //添加以下代码
        maven {
		https://dl.bintray.com/zhichengwu/ABanner
		}
    }
    ...
		
````

2、 在 `app`中的 `gradle` 中添加依赖

````
dependencies {
		implementation fileTree(dir: 'libs', include: ['*.jar'])
	    ...
	    //添加以下代码
		implementation 'com.zhicheng:ABanner:0.0.3'
	}
````

3、 在`xml`布局文件中使用`ABanner`控件

````
   <com.zhicheng.androidbanner.ABanner
        android:layout_width="match_parent"
        android:layout_height="220dp">
    </com.zhicheng.androidbanner.ABanner>

````

4、在代码中对`Abanner`样式等进行设置

````
	mBanner.setBannerTransformerType(ABanner.BannerType.GALLERY) //设置类型
                .setAdapter(new BannerAdapter(true)) //设置 adapter
                .setBannerTransformerDuration(1000) //设置滚动动画时间
                .setBannerTrannerScale(0.8f) // 设置画廊模式的缩放
                .setIndicatorType(ABanner.IndicatorType.CUSTOM_IMAGE) //设置指示器类型
                .setIndicatorPosition(ABanner.IndicatorPosition.CENTER_BOTTOM,new Rect(80,50,80,20)) //设置指示器位置，并设置margin值
                .startLoop(3000);
	
````

5、设置指示器

````
	//获取到指示器后，根据类型强转，然后进行个性化设置
   ShapeBannerIndicator bannerIndicator = (ShapeBannerIndicator)mBanner.getBannerIndicator();
   bannerIndicator.setSelectColor(Color.RED); //设置选中颜色
   bannerIndicator.setNormalColor(Color.LTGRAY); //设置未选中颜色
   bannerIndicator.setNormalSize(new ASize(10,10)); //设置未选中大小
   bannerIndicator.setSelectSize(new ASize(10,10)); //设置选中大小
   bannerIndicator.reset(); //设置完成后请调用该方法

````


***更多设置未详尽列出....***

## 高级设置

- 自定义指示器

请自定义一个继承`BaseBannerIndicator`的类， 并且实现以下方法

````
	//当每一个item需要更新的时候调用，selected `true`表示为选中状态的视图
	public abstract void updateIndicatorItemView(View view,boolean selected);
	//表示获取一个item的视图
	public abstract View getIndicatorItemView(boolean selected);

````

然后调用一下方法

````
	aABanner.setCustomIndicator(aBaseBannerIndicator);

````

- 自定义滚动动画

请自定义一个类继承自`BaseBannerTransformer`并重写其中的方法，然后调用一下方法

````
	aABanner.setCustomTransformer(aBaseBannerTransformer);
	
````







