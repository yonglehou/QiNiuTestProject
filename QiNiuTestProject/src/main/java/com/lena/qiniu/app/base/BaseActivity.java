package com.lena.qiniu.app.base;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by lenayan on 14-12-2.
 */
public class BaseActivity extends ActionBarActivity {

    private ProgressDialog mProgressDialog;

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isProgressDialogShowing())
            hideProgressDialog();
    }

    protected boolean isProgressDialogShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    protected void showProgressDialog(boolean cancelable) {
        showProgressDialog("", cancelable);
    }

    protected void showProgressDialog() {
        showProgressDialog("", true);
    }

    protected void showProgressDialog(String title) {
        showProgressDialog(title, true);
    }

    protected void showProgressDialog(String title, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (!TextUtils.isEmpty(title))
            mProgressDialog.setMessage(title);
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setCanceledOnTouchOutside(cancelable);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

}
