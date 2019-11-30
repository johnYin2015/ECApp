package com.diabin.latte.core.net.rx;

import android.content.Context;

import com.diabin.latte.core.net.RestCreator;
import com.diabin.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {
    private  String mUrl;
    private WeakHashMap<String,Object> mParams = RestCreator.getParams();
    private  RequestBody mRequestBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value){
        mParams.put(key,value);
        return this;
    }

    /**
     *
     * @param raw 原始数据
     * @return
     */
    public final RxRestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    //file赋值
    public final RxRestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    /**
     * file赋值
     * @param file String类型
     * @return
     */
    public final RxRestClientBuilder file(String file){
        this.mFile = new File(file);
        return this;
    }

    //构建
    public final RxRestClient build(){
        return new RxRestClient(mUrl,mParams,mRequestBody,mContext,mLoaderStyle,mFile);
    }
}
