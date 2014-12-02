package com.lena.qiniu.app.utils;

import android.util.Log;

/**
 * Created by lenayan on 14-6-23.
 */
public class LogUtil {

    private final static String LOG_TAG = "SuDiYi";
    private static boolean DEBUG = false;

    public static void openLog() {
        DEBUG = true;
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(LOG_TAG, msg);
        }
    }

    public static void e(String msg) {
        e(LOG_TAG, msg);
    }

    public static void e(String msg, Throwable throwable) {
        e(LOG_TAG, msg, throwable);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.e(tag, msg, throwable);
        }
    }

}
