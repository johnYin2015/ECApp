package com.diabin.latte.core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    WebView createWebView(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(true);

        //cookie
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        CookieManager.setAcceptFileSchemeCookies(true);

        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(true);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(true);
        //允许截图
        webView.setDrawingCacheEnabled(true);

        //屏幕长按事件
        webView.setOnLongClickListener(v -> true);

        final WebSettings webSettings = webView.getSettings();
        //启用js
        webSettings.setJavaScriptEnabled(true);
        //获取用户代理信息
        final String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "Latte");//判断是否app打开页面
        //隐藏缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        //禁止缩放
        webSettings.setSupportZoom(false);

        //文件权限
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);

        //缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }
}
