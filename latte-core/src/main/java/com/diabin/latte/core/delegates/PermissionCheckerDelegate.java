package com.diabin.latte.core.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.core.ui.camera.CameraImageBean;
import com.diabin.latte.core.ui.camera.LatteCamera;
import com.diabin.latte.core.ui.camera.RequestCodes;
import com.diabin.latte.core.util.callback.CallbackManager;
import com.diabin.latte.core.util.callback.CallbackType;
import com.diabin.latte.core.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 底部权限申请对话框在此界面中
 * Created by 傅令杰 on 2017/4/2
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {

    }

    //不是直接调用方法。仅仅生成代码使用
    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamera() {
        LatteCamera.start(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStorageDenied() {
        Toast.makeText(getContext(), "不给存储权限", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStroageNever() {
        Toast.makeText(getContext(), "永久拒绝存储权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //拍照、相册选择、裁剪相片、裁剪错误回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO://拍照
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    if (getContext() != null) {
                        UCrop.of(resultUri, resultUri)
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.PICK_PHOTO://从系统相册中选择
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        if (pickPath != null && getContext() != null) {
                            UCrop.of(pickPath, Uri.parse(pickCropPath))
                                    .withMaxResultSize(400, 400)
                                    .start(getContext(), this);
                        }
                    }
                    break;
                case RequestCodes.CROP_PHOTO://裁剪图片
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked") final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}