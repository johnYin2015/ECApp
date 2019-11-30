package com.diabin.latte.core.net.rx;

import android.content.Context;

import com.diabin.latte.core.net.HttpMethod;
import com.diabin.latte.core.net.RestCreator;
import com.diabin.latte.core.ui.loader.LatteLoader;
import com.diabin.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.diabin.latte.core.net.HttpMethod.DELETE;
import static com.diabin.latte.core.net.HttpMethod.GET;
import static com.diabin.latte.core.net.HttpMethod.POST;
import static com.diabin.latte.core.net.HttpMethod.POST_RAW;
import static com.diabin.latte.core.net.HttpMethod.PUT;
import static com.diabin.latte.core.net.HttpMethod.PUT_RAW;
import static com.diabin.latte.core.net.HttpMethod.UPLOAD;

public final class RxRestClient {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;

    public RxRestClient(String URL,
                        WeakHashMap<String, Object> params,
                        RequestBody requestBody,
                        Context context,
                        LoaderStyle loaderStyle,
                        File FILE) {
        this.URL = URL;
        PARAMS.putAll(params);
        this.BODY = requestBody;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = FILE;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();

        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW://要求body不为空，参数必须为空
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                MultipartBody.Part part =
                        MultipartBody.Part.
                                createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, part);
                break;
            default:
                break;

        }

        return observable;
    }

    public final Observable<String> get() {
        return request(GET);
    }

    public final Observable<String> post() {
        //post_raw body not null
        if (BODY == null) {
            return request(POST);
        } else {
            //参数非空
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null");
            }
            return request(POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(PUT);
        } else {
            //参数非空
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null");
            }
            //参数要为空
            return request(PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(DELETE);
    }

    public final Observable<String> upload() {
        return request(UPLOAD);
    }
}
