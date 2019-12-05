package com.diabin.latte.core.util.callback;

import android.support.annotation.NonNull;

/**
 * 描述：通用的callback
 * 作者：johnyin2015
 * 日期：2019/12/4 19:54
 */
public interface IGlobalCallback<T> {
    void executeCallback(@NonNull T t);
}
