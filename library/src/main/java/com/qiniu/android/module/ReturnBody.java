package com.qiniu.android.module;

/**
 * Created by lenayan on 14-12-2.
 */
public class ReturnBody {
    String name = "$(fname)";
    String size = "$(fsize)";
    String w = "$(imageInfo.width)";
    String h = "$(imageInfo.height)";
    String hash = "$(etag)";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
