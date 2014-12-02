package com.qiniu.android.module;

public class UpLoadPutPolicy {

    private String scope;
    private String key;
    private long deadline;
    private ReturnBody returnBody;

    public UpLoadPutPolicy(String scope, String key, long deadline) {
        this.scope = scope;
        this.key = key;
        this.deadline = deadline;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }


    public ReturnBody getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(ReturnBody returnBody) {
        this.returnBody = returnBody;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
