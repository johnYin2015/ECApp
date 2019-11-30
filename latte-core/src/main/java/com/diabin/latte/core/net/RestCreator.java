package com.diabin.latte.core.net;

import com.diabin.latte.core.app.ConfigKeys;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {
    private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();

    public static final WeakHashMap<String, Object> getParams() {
        return PARAMS;
    }

    public static final RestService getRestService() {
        return RestCreator.RestServiceHolder.REST_SERVICE;
    }

    public static final RxRestService getRxRestService() {
        return RestCreator.RxRestServiceHolder.RX_REST_SERVICE;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL =
                (String) Latte.getConfigurations().get(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(RestCreator.OkHttpHolder.OKHTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava适配器
                .build();
    }

    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;

        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static final ArrayList<Interceptor> INTERCEPTORS =
                (ArrayList<Interceptor>) Latte.getConfigurations().get(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptors() {

            //往okhttp中添加拦截器
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }

        private static final OkHttpClient OKHTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE
                = RestCreator.RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);

    }

    private static final class RxRestServiceHolder {
        private static final RxRestService RX_REST_SERVICE
                = RestCreator.RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }
}
