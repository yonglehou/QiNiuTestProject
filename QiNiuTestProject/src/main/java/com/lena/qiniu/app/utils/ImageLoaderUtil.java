package com.lena.qiniu.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.lena.qiniu.app.cache.FileCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.concurrent.Executor;

public class ImageLoaderUtil {
    private static ImageLoader sImageLoader = ImageLoader.getInstance();
    private static DisplayImageOptions sDisplayImageOptions;

    static {
        sDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(0)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .handler(new Handler())
                .build();
    }

    public static void init(Context context) {

        Executor executor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executor = AsyncTask.THREAD_POOL_EXECUTOR;
        }
        final int maxMemory =
                (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheExtraOptions(ScreenUtil.sScreenWidth, ScreenUtil.sScreenHeight, null)
                .taskExecutor(executor)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(cacheSize))
                .diskCache(new UnlimitedDiscCache(FileCache.getsInstance().getCacheFile(FileCache.IMAGE_CACHE_FLODER_SUFFIX, FileCache.sCacheRoot))) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();
        sImageLoader.init(imageLoaderConfiguration);
    }


    public static void displayImage(String uri, ImageView imageView) {
        sImageLoader.displayImage(uri, imageView, sDisplayImageOptions);
    }

    public static void displayImage(String uri, ImageView imageView,
                                    ImageLoadingListener listener) {
        sImageLoader.displayImage(uri, imageView, sDisplayImageOptions, listener);
    }

    public static void displayImage(String uri, ImageView imageView,
                                    ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        sImageLoader.displayImage(uri, imageView, sDisplayImageOptions, listener, progressListener);
    }

    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options,
                                    ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        sImageLoader.displayImage(uri, imageView, options, listener, progressListener);
    }

    public static void loadImage(String uri, ImageSize imageSize, ImageLoadingListener listener) {
        sImageLoader.loadImage(uri, imageSize, sDisplayImageOptions, listener);
    }

    public static AbsListView.OnScrollListener getPauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener onScrollListener) {
        return new PauseOnScrollListener(sImageLoader, pauseOnScroll, pauseOnFling, onScrollListener);
    }

    public static void clearDiskCache() {
        sImageLoader.clearDiskCache();
    }

    public static void clearMemoryCache() {
        sImageLoader.clearMemoryCache();
    }

    public static void clearImageCache() {
        sImageLoader.clearMemoryCache();
        sImageLoader.clearDiskCache();
    }


}
