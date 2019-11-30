package com.diabin.latte.core.net.callback;

import android.os.Handler;

import com.diabin.latte.core.app.ConfigKeys;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.ui.loader.LatteLoader;
import com.diabin.latte.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request,
                            ISuccess success,
                            IError error,
                            IFailure failure,
                            LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError();
            }
        }
        stopLoading();
    }

    private void stopLoading() {
        final int delay = (int) Latte.getConfigurations().get(ConfigKeys.LOADER_DELAY);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();//关闭进度条
                }
            }, delay);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }
}
