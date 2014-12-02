package com.lena.qiniu.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lena.qiniu.app.base.BaseActivity;
import com.lena.qiniu.app.cache.FileCache;
import com.lena.qiniu.app.utils.ImageLoaderUtil;
import com.lena.qiniu.app.utils.ScreenUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    private final int REQUEST_CODE_CARMER = 0X10;
    private final int REQUEST_CODE_GALLERY = 0X11;
    private final String IMAGE_NAME = "IMAGE_ID_CARD_FRONT";
    private String mImagePath = null;

    @InjectView(R.id.imageview)
    ImageView mImageView;
    @InjectView(R.id.upload_image)
    Button mUploadImageButton;
    @InjectView(R.id.upload_vedio)
    Button mUploadVedioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(mUploadImageButton);
        registerForContextMenu(mUploadVedioButton);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.upload_image);
        getMenuInflater().inflate(R.menu.image_upload_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.carmer:
                mImagePath = initImagePath();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(mImagePath));
                startActivityForResult(intent, REQUEST_CODE_CARMER);
                break;
            default:
            case R.id.gallery:
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CARMER:
                    getImageFile();
                    break;
                case REQUEST_CODE_GALLERY:
                    if (data != null)
                        mImagePath = data.getData().toString();
                    getImageFile();
                    break;
                default:
                    break;
            }
        }
    }

    private String initImagePath() {
        return FileCache.getsInstance().getImageFileCachePath(this, IMAGE_NAME);
    }

    public void onUploadImageClicked(View view) {
        Toast.makeText(this, "ImageView", Toast.LENGTH_LONG).show();
        openContextMenu(view);
    }

    public void onUploadVedioClicked(View view) {
        Toast.makeText(this, "Vedio", Toast.LENGTH_LONG).show();
    }

    private void getImageFile() {
        if (!TextUtils.isEmpty(mImagePath)) {
            ImageLoaderUtil.loadImage(mImagePath, new ImageSize(ScreenUtil.sScreenWidth / 2, ScreenUtil.sScreenHeight / 2), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    showProgressDialog(false);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    hideProgressDialog();
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    hideProgressDialog();
                    mImageView.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    hideProgressDialog();
                }
            });
        }
    }
}
