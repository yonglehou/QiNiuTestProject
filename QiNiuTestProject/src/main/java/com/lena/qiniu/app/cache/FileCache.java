package com.lena.qiniu.app.cache;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import com.lena.qiniu.app.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenayan-x230s on 6/26/14.
 */
public class FileCache {

    private static final String LOG_TAG = "FileCache";

    public static final String IMAGE_CACHE_FLODER_SUFFIX = "/ImageCache";
    public static final String FILE_CACHE_FLODER_SUFFIX = "/FileCache";
    public static final String FILE_DOWNLOAD_FLODER_SUFFIX = "/files";

    private static final byte[] LRU_LOCK = new byte[0];

    public static String sRoot;
    public static String sCacheRoot;
    public static String sImageCacheRootPath;
    public static String sFileCacheRootPath;
    public static String sDownLoadFileRootPath;
    private static FileCache sInstance = new FileCache();
    private LinkedList<String> mFileLRU = new LinkedList<String>();

    private FileCache() {
        //TODO  remove expired caches .
    }

    public static void install(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // SD-card available
            sRoot = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Android/data/" + context.getPackageName();
            sCacheRoot = sRoot + "/cache";
            File file = new File(sCacheRoot);
            if (!file.exists()) {
                if (!file.mkdirs())
                    sCacheRoot = context.getCacheDir().getAbsolutePath();
            }
        } else {
            sCacheRoot = context.getCacheDir().getAbsolutePath();
        }
        initImageCacheFile();
        initFileCacheFile();
        initDownLoadFilesPath();
    }

    private static void initImageCacheFile() {
        sImageCacheRootPath = FileCache.sCacheRoot + IMAGE_CACHE_FLODER_SUFFIX;
        File file = new File(sImageCacheRootPath);
        if (!file.exists()) {
            if (!file.mkdirs())
                sImageCacheRootPath = FileCache.sCacheRoot;
        }
    }

    private static void initFileCacheFile() {
        sFileCacheRootPath = FileCache.sCacheRoot + FILE_CACHE_FLODER_SUFFIX;
        File file = new File(sFileCacheRootPath);
        if (!file.exists()) {
            if (!file.mkdirs())
                sFileCacheRootPath = FileCache.sCacheRoot;
        }
    }

    private static void initDownLoadFilesPath() {
        sDownLoadFileRootPath = FileCache.sRoot + FILE_DOWNLOAD_FLODER_SUFFIX;
        File file = new File(sDownLoadFileRootPath);
        if (!file.exists()) {
            if (!file.mkdirs())
                sDownLoadFileRootPath = FileCache.sRoot;
        }
    }

    public static FileCache getsInstance() {
        return sInstance;
    }

    public void clearImageCache() {
        FileUtil.deleteChild(sImageCacheRootPath);
    }

    public File getCacheFile(String key, String roortPath) {
        return new File(getValidFloderPath(roortPath), escapeFileName(key));
    }

    public String getValidFloderPath(String florderPath) {
        File file = new File(florderPath);
        if (!file.exists() && !file.mkdirs()) {
            LogUtil.e(LOG_TAG, "Create floder falure.Key = " + florderPath);
        }
        return florderPath;
    }

    private String escapeFileName(String key) {
        return key.replace('/', '_');
    }

    public void saveImageFile(final String fileName, final Bitmap bitmap) {
        new Thread(new Runnable() {

            public void run() {
                FileOutputStream out = null;
                try {
                    File imageFile = getCacheFile(fileName + ".jpg", sImageCacheRootPath);
                    out = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(LOG_TAG, "Save Image cast Exception : " + e.getMessage());
                } finally {
                    try {
                        if (out != null)
                            out.close();
                    } catch (IOException e) {
                        LogUtil.e(LOG_TAG, "Save Image cast IOException : " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    public String getImageFileCachePath(Context context, String fileName) {
        String path;
        if (isIntentAvailable(context, MediaStore.ACTION_IMAGE_CAPTURE)) {
            path = sImageCacheRootPath + "/" + fileName + formatDateStamp(System.currentTimeMillis())
                    + ".jpg";
        } else {
            path = sImageCacheRootPath + "/temp" + formatDateStamp(System.currentTimeMillis()) + ".jpg";
        }
        return "file:///" + path;
    }

    public String getImageFilePath() {
        return sImageCacheRootPath + "/" + formatDateStamp(System.currentTimeMillis()) + ".jpg";
    }

    public String formatDateStamp(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

    private boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
