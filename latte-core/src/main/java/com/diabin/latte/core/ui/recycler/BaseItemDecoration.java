package com.diabin.latte.core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 16:14
 */
public class BaseItemDecoration extends DividerItemDecoration {

    private BaseItemDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseItemDecoration create(int color,int size) {
        return new BaseItemDecoration(color,size);
    }
}
