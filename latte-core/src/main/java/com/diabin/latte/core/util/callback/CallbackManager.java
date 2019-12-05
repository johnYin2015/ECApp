package com.diabin.latte.core.util.callback;

import java.util.WeakHashMap;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/4 19:57
 */
public final class CallbackManager {

    private static final WeakHashMap<Object,IGlobalCallback> CALLBACKS = new WeakHashMap();

    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object tag, IGlobalCallback callback) {
        CALLBACKS.put(tag, callback);
        return this;
    }

    public IGlobalCallback getCallback(Object tag) {
        return CALLBACKS.get(tag);
    }
}
