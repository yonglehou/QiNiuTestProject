package com.lena.qiniu.app.utils;

public class Base64 {
    public static String encode(String data) {
        return android.util.Base64.encodeToString(data.getBytes(),
                android.util.Base64.URL_SAFE | android.util.Base64.NO_WRAP);
    }

    public static byte[] decode(String data) {
        return android.util.Base64.decode(data.getBytes(),
                android.util.Base64.URL_SAFE | android.util.Base64.NO_WRAP);
    }

    public static String encode(byte[] data) {
        return android.util.Base64.encodeToString(data,
                android.util.Base64.URL_SAFE | android.util.Base64.NO_WRAP);
    }
}
