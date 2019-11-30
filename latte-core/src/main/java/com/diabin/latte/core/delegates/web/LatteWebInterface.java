package com.diabin.latte.core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.core.delegates.web.event.Event;
import com.diabin.latte.core.delegates.web.event.EventManager;
import com.diabin.latte.core.util.log.LatteLogger;

/**
 * 创建JS交互的本地对象类
 */
final class LatteWebInterface {

    private final WebDelegate DELEGATE;

    private LatteWebInterface(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    static LatteWebInterface create(WebDelegate delegate) {
        return new LatteWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        LatteLogger.d("WEB_EVENT",params);
        if (event!=null){
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }

        return null;
    }
}
