package com.lena.qiniu.app.application;

import android.app.Application;

import com.lena.qiniu.app.cache.FileCache;
import com.lena.qiniu.app.utils.ImageLoaderUtil;
import com.lena.qiniu.app.utils.LogUtil;
import com.lena.qiniu.app.utils.ScreenUtil;

/**
 * Created by lenayan on 14-12-2.
 */
public class QNApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initial cache file.
        FileCache.install(this);
//        Initial ScreenUtil before initial ImageLoader
        ScreenUtil.init(this);

//        Initial ImageLoader
        ImageLoaderUtil.init(this);
        LogUtil.openLog();
    }
}
