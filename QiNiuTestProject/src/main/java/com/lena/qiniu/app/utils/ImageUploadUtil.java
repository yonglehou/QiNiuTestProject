package com.lena.qiniu.app.utils;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.lena.qiniu.app.BuildConfig;
import com.qiniu.android.QiniuKeys;
import com.qiniu.android.module.UpLoadPutPolicy;
import com.qiniu.android.utils.UrlSafeBase64;

public class ImageUploadUtil {

    public static final String CHARSET = "UTF-8";
    public static final long DEADLINE = System.currentTimeMillis() / 1000 + QiniuKeys.IMAGE_DEALINE;

    public static String getToken(String imageName) {
        String uptoken = null;
        try {
            UpLoadPutPolicy upLoadPutPolicy = new UpLoadPutPolicy(BuildConfig.QINIU_BITBUCKET_NAME, imageName, DEADLINE);
            String encodedPutPolicy = UrlSafeBase64.encodeToString(JSON.toJSONString(upLoadPutPolicy));
            byte[] sign = HmacSha1.getSignature(encodedPutPolicy.getBytes(CHARSET), BuildConfig.QINIU_SECKET_KEY.getBytes(CHARSET));
            String encodedSign = UrlSafeBase64.encodeToString(sign);
            uptoken = BuildConfig.QINIU_ACCECC_KEY + ":" + encodedSign + ":" + encodedPutPolicy;
        } catch (Exception e) {
            LogUtil.e("Encode image upload token error");
        }
        LogUtil.i("uptoken----------" + uptoken);
        return uptoken;
    }

    public static String appendImageUrl(String imageUrl) {
        String tmpImageUrl = imageUrl + "?e=" + DEADLINE;
        try {
            String token = UrlSafeBase64.
                    encodeToString(
                            HmacSha1.getSignature(tmpImageUrl.getBytes(CHARSET), BuildConfig.QINIU_SECKET_KEY.getBytes(CHARSET))
                    );
            Uri.Builder builder = new Uri.Builder();
            builder.path("");
            builder.appendQueryParameter("e", String.valueOf(DEADLINE));
            builder.appendQueryParameter("token", BuildConfig.QINIU_ACCECC_KEY + ":" + token);
            return imageUrl + "?" + builder.build().getQuery();
        } catch (Exception e) {
            LogUtil.e("Encode image upload token error");
        }
        return "";
    }

    public static String buildFinaImagelUrl(String key) {
        String redirect = String.format(String.format("http://%1$s/%2$s", BuildConfig.QINIU_DOMIN_NAME, key));
        String finalImageUrl = appendImageUrl(redirect);
        LogUtil.i("FilnalImageUrl: " + finalImageUrl);
        return finalImageUrl;
    }

}
