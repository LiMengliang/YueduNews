package com.redoc.yuedu.utilities.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.redoc.yuedu.R;

import java.io.File;

/**
 * Created by limen on 2016/5/29.
 */
public class LoadImageUtilities {
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    private static String cacheDirectory;
    public static String getCacheDirectory() {
        return cacheDirectory;
    }

    /** 初始化ImageLoader */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, context.getPackageName() + "/cache/");// 获取到缓存的目录地址
        Log.d("cacheDir", cacheDir.getPath());
        cacheDirectory = cacheDir.getPath();
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(300, 300) // max width, max
                // height，即保存的每个缓存文件的最大长宽
                // .discCacheExtraOptions(480, 800, CompressFormat.JPEG,
                // 75, null) // Can slow ImageLoader, use it carefully
                // (Better don't use it)设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                        // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024
                        // * 1024)) // You can pass your own memory cache
                        // implementation你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                        // /.discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
                        // 加密
                        // .discCacheFileNameGenerator(new
                        // HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .discCacheFileCount(100) //缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 *
                        1000, 30 * 1000)) // connectTimeout (5 s),
                        // readTimeout(30)// 超时时间
                // .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    private static DisplayImageOptions defaultOption = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.default_digset_image)
                        // // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.default_digset_image)
                        // // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.default_digset_image)
                .cacheInMemory(false)
                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                        // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
                        // 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                        // .decodingOptions(android.graphics.BitmapFactory.Options
                        // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                        // 设置图片下载前的延迟
                        // .delayBeforeLoading(int delayInMillis)//int
                        // delayInMillis为你设置的延迟时间
                        // 设置图片加入缓存前，对bitmap进行设置
                        // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                        // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                // .displayer(new FadeInBitmapDisplayer(500))// 淡入
                // don't use RoundedBitmapDisplyaer, it will create RGB_888 image
                // .displayer(new RoundedBitmapDisplayer(7))
                .build();

    private static DisplayImageOptions nonCacheOption = new DisplayImageOptions.Builder()
            // // 设置图片在下载期间显示的图片
            .showImageOnLoading(R.drawable.default_digset_image)
                    // // 设置图片Uri为空或是错误的时候显示的图片
            .showImageForEmptyUri(R.drawable.default_digset_image)
                    // // 设置图片加载/解码过程中错误时候显示的图片
            .showImageOnFail(R.drawable.default_digset_image)
            .cacheInMemory(false)
                    // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(false)
                    // 设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
                    // 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                    // .decodingOptions(android.graphics.BitmapFactory.Options
                    // decodingOptions)//设置图片的解码配置
            .considerExifParams(true)
                    // 设置图片下载前的延迟
                    // .delayBeforeLoading(int delayInMillis)//int
                    // delayInMillis为你设置的延迟时间
                    // 设置图片加入缓存前，对bitmap进行设置
                    // 。preProcessor(BitmapProcessor preProcessor)
            .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                    // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
            // .displayer(new FadeInBitmapDisplayer(500))// 淡入
            // don't use RoundedBitmapDisplyaer, it will create RGB_888 image
                    // .displayer(new RoundedBitmapDisplayer(7))
            .build();

    public static void displayImage(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView, defaultOption);
    }

    public static void displayLocalImage(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView, nonCacheOption);
    }

    public static Bitmap loadBitmapFromUriSync(String uri) {
        return imageLoader.loadImageSync(uri, defaultOption);
    }

    public static void loadBitmapFromUriAsync(String uri, ImageLoadingListener listener) {
        imageLoader.loadImage(uri, defaultOption, listener);
    }

    public static Bitmap resizeBitmapToAcceptableSize(Bitmap bitmap)
    {
        if(bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if(width * height > 300 * 300) {
                float widthScale = 300/(float)width;
                float heightScale = 300/(float)height;
                float scale = widthScale > heightScale ? heightScale : widthScale;
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            }
        }
        return bitmap;
    }
}
