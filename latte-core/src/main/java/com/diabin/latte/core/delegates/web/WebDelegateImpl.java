package com.diabin.latte.core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.diabin.latte.core.delegates.IPageLoadListener;
import com.diabin.latte.core.delegates.web.chromeclient.WebChromeClientImpl;
import com.diabin.latte.core.delegates.web.client.WebViewClientImpl;
import com.diabin.latte.core.delegates.web.route.RouteKeys;
import com.diabin.latte.core.delegates.web.route.Router;

public class WebDelegateImpl extends WebDelegate {

    private IPageLoadListener mIPageLoadListener = null;

    //简单工厂方法实例化
    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl webDelegateImpl = new WebDelegateImpl();
        webDelegateImpl.setArguments(args);
        return webDelegateImpl;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        if (getUrl() != null) {
            //用原生方式模拟web跳转，进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    public void setPageLoadListener(IPageLoadListener pageLoadListener) {
        this.mIPageLoadListener = pageLoadListener;
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl webViewClientImpl = new WebViewClientImpl(this);
        webViewClientImpl.setPageLoadListener(mIPageLoadListener);
        return webViewClientImpl;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
