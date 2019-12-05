package com.diabin.latte.core.ui.camera;

import com.yalantis.ucrop.UCrop;

/**
 * Created by 傅令杰
 * 请求码存储
 */

public class RequestCodes {
    public static final int TAKE_PHOTO = 4;
    public static final int PICK_PHOTO = 5;//选
    public static final int CROP_PHOTO = UCrop.REQUEST_CROP;//剪裁
    public static final int CROP_ERROR = UCrop.RESULT_ERROR;
    public static final int SCAN = 7;//二维码
}
