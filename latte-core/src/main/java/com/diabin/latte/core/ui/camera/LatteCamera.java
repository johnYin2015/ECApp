package com.diabin.latte.core.ui.camera;

import android.net.Uri;

import com.diabin.latte.core.delegates.PermissionCheckerDelegate;
import com.diabin.latte.core.util.FileUtil;


/**
 * Created by 傅令杰
 * 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile(
                        "crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
