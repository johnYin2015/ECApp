package com.diabin.latte.core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.diabin.latte.core.app.Latte;

public class DimenUtil {

    //获取屏幕宽度
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight(){
        final Resources resources = Latte.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
