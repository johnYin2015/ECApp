package com.diabin.latte.core.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseInterceptor implements Interceptor {

    protected static LinkedHashMap<String,String> getUrlParamters(Chain chain) {
        final LinkedHashMap params = new LinkedHashMap();//增删快.

        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }

        return params;
    }

    protected static String getUrlParamters(Chain chain,String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected static LinkedHashMap<String,String> getBodyParameters(Chain chain) {
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();

        final FormBody formBody = (FormBody) chain.request().body();
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i),formBody.value(i));
        }

        return params;
    }

    protected String getBodyParameter(Chain chain,String key){
        return getBodyParameters(chain).get(key);
    }

}
