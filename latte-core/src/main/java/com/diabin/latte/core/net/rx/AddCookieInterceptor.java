package com.diabin.latte.core.net.rx;

import android.annotation.SuppressLint;

import com.diabin.latte.core.ui.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class AddCookieInterceptor implements Interceptor {
    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();

        // noinspection ResultOfMethodCallIgnored
        Observable.just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(cookie -> {
                    //原生的API请求添加cookie header
                    builder.addHeader("Cookie",cookie);
                });

        return chain.proceed(builder.build());
    }
}
