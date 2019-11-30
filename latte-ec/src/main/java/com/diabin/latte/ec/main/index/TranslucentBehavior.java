package com.diabin.latte.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.ui.recycler.RgbValue;
import com.diabin.latte.core.util.log.LatteLogger;
import com.diabin.latte.ec.R;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 17:01
 */
@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private final int SHOW_SPEED = 3;
    //255 124 2
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar toolbar, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, toolbar, target, dx, dy, consumed, type);
        mDistanceY += dy;//增加滑动的距离

        final int toolbarHeight = Latte.getApplicationContext().getResources()
                .getDimensionPixelOffset(R.dimen.header_height) + 300;//延长滑动过程

        if (mDistanceY <= 0) {
            toolbar.setBackgroundColor(Color.argb(0, RGB_VALUE.red(), RGB_VALUE.green(),
                    RGB_VALUE.blue()));
        }
        //当滑动的距离小于toolbar高度时，颜色渐变
        else if (mDistanceY > 0 && mDistanceY <= toolbarHeight) {
            final float scale = (float) (mDistanceY) / toolbarHeight;
            final int alpha = Math.round(scale * 255);//四舍五入
            LatteLogger.d("onNestedPreScroll", "alpha = " + alpha);
            toolbar.setBackgroundColor(Color.argb(alpha, RGB_VALUE.red(), RGB_VALUE.green(),
                    RGB_VALUE.blue()));
        } else {
            toolbar.setBackgroundColor(Color.argb(255, RGB_VALUE.red(), RGB_VALUE.green(),
                    RGB_VALUE.blue()));
        }
    }
}
