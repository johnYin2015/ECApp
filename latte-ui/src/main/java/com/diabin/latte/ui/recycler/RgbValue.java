package com.diabin.latte.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 17:10
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red,green,blue);
    }

}
