package com.diabin.latte.core.net.download;

import android.os.AsyncTask;

import com.diabin.latte.core.net.RestCreator;
import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.IRequest;
import com.diabin.latte.core.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    private final String URL;
    private final Map<String, Object> PARAMS;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public DownloadHandler(String URL,
                           Map<String, Object> PARAMS,
                           String DOWNLOAD_DIR,
                           String EXTENSION,
                           String NAME,
                           IRequest REQUEST,
                           ISuccess SUCCESS,
                           IError ERROR,
                           IFailure FAILURE) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
    }

    public final void download() {
        RestCreator.getRestService()
                .download(this.URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            //下载完成，持久化到本地
                            SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //避免下载不全
                            if (saveFileTask.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
