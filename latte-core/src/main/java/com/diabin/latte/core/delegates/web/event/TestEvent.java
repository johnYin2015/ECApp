package com.diabin.latte.core.delegates.web.event;

import android.webkit.WebView;
import android.widget.Toast;

public class TestEvent extends Event {

    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_LONG).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(() -> webView.evaluateJavascript("nativeCall();", null));
        }
        return null;
    }
}
