package com.diabin.latte.core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.diabin.latte.core.app.ConfigKeys;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.delegates.IPageLoadListener;
import com.diabin.latte.core.delegates.web.WebDelegate;
import com.diabin.latte.core.delegates.web.route.Router;
import com.diabin.latte.core.ui.loader.LatteLoader;
import com.diabin.latte.core.ui.storage.LattePreference;
import com.diabin.latte.core.util.log.LatteLogger;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();//不变

    public void setPageLoadListener(IPageLoadListener pageLoadListener) {
        this.mIPageLoadListener = pageLoadListener;
    }

    public WebViewClientImpl(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        //转圈
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(LatteLoader::stopLoading, 1000);
    }

    //获取浏览器cookie
    public void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        final String webHost = (String) Latte.getConfigurations().get(ConfigKeys.WEB_HOST);
        if (webHost != null) {
            if (manager.hasCookies()) {
                final String cookie = manager.getCookie(webHost);
                if (cookie != null && !cookie.equals("")) {
                    LattePreference.addCustomAppProfile("cookie", cookie);
                }
            }
        }
    }
}
