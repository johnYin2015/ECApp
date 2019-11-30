package com.diabin.latte.core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.delegates.web.WebDelegate;
import com.diabin.latte.core.delegates.web.WebDelegateImpl;

public class Router {
    //用静态内部类单例模式实例化
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null");
        }
    }

    //android_asset
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    public boolean handleWebUrl(WebDelegate delegate, String url) {

        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        //防止内层跳外层不跳
        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegateImpl = WebDelegateImpl.create(url);
        topDelegate.start(webDelegateImpl);

        return true;
    }

    private void callPhone(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri uri = Uri.parse(url);
        intent.setData(uri);
        ContextCompat.startActivity(context, intent, null);
    }
}
