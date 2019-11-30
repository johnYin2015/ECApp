package com.diabin.latte.core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.diabin.latte.core.delegates.web.WebDelegate;

public abstract class Event implements IEvent {
    private String mAction;
    private Context mContext;
    private WebDelegate mDelegate;
    private String mUrl;
    private WebView mWebView;

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public WebDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate delegate) {
        this.mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public WebView getWebView() {
        return mDelegate.getWebView();
    }


}
