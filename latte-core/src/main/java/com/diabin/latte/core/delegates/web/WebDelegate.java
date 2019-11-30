package com.diabin.latte.core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.diabin.latte.core.app.ConfigKeys;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 网络服务的抽象基类
 * 作者：johnyin2015
 * 日期：2019/11/16 23:22
 */
public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer {

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    @SuppressWarnings({"FieldCanBeLocal"})
    private String mUrl = null;
    private boolean mIsWebViewAvailable = false;
    private LatteDelegate mTopDelegate;

    public WebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    @SuppressWarnings({"unchecked"})
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitializer initializer = setInitializer();
            if (initializer != null) {
                WeakReference<WebView> weakReference =
                        new WeakReference(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = weakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                String name = (String) Latte.getConfigurations().get(ConfigKeys.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(LatteWebInterface.create(this), name);
                mIsWebViewAvailable = true;
            } else {
                //noinspection SpellCheckingInspection
                throw new NullPointerException("Initilizer is null");
            }
        }
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView is null");
        }

        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("mUrl is null");
        }

        return mUrl;
    }

    public LatteDelegate getTopDelegate() {
        if (mTopDelegate == null) {
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public void setTopDelegate(LatteDelegate topDelegate) {
        this.mTopDelegate = topDelegate;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
