package com.diabin.latte.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * 扫描框
 * Created by 傅令杰
 */

public class LatteViewFinderView extends ViewFinderView {

    public LatteViewFinderView(Context context) {
        this(context, null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;//正方形
        mBorderPaint.setColor(Color.YELLOW);//边框颜色
        mLaserPaint.setColor(Color.YELLOW);
    }
}
