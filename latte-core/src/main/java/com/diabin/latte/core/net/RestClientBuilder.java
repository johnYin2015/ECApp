package com.diabin.latte.core.net;

import android.content.Context;

import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.IRequest;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private  String mUrl;
    private  Map<String,Object> mParams = RestCreator.getParams();
    private  IRequest mRequest;
    private  ISuccess mSuccess;
    private  IError mError;
    private  IFailure mFailure;
    private  RequestBody mRequestBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        mParams.put(key,value);
        return this;
    }

    /**
     *
     * @param raw 原始数据
     * @return
     */
    public final RestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    //请求
    public final RestClientBuilder request(IRequest request){
        this.mRequest = request;
        return this;
    }

    //成功
    public final RestClientBuilder success(ISuccess success){
        this.mSuccess = success;
        return this;
    }

    //错误
    public final RestClientBuilder error(IError error){
        this.mError = error;
        return this;
    }
    //失败
    public final RestClientBuilder failure(IFailure failure){
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    //file赋值
    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    /**
     * file赋值
     * @param file String类型
     * @return
     */
    public final RestClientBuilder file(String file){
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.mExtension = extension;
        return this;
    }

    //构建
    public final RestClient build(){
        return new RestClient
                (mUrl,mParams,mDownloadDir,mExtension,mName,mRequest,mSuccess,mError,
                        mFailure,mRequestBody,mContext,mLoaderStyle,mFile);
    }
}
