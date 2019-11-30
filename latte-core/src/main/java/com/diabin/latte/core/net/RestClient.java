package com.diabin.latte.core.net;

import android.content.Context;

import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.IRequest;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.net.callback.RequestCallbacks;
import com.diabin.latte.core.ui.loader.LatteLoader;
import com.diabin.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import okhttp3.RequestBody;

import static com.diabin.latte.core.net.HttpMethod.DELETE;
import static com.diabin.latte.core.net.HttpMethod.GET;
import static com.diabin.latte.core.net.HttpMethod.POST;
import static com.diabin.latte.core.net.HttpMethod.POST_RAW;
import static com.diabin.latte.core.net.HttpMethod.PUT;
import static com.diabin.latte.core.net.HttpMethod.PUT_RAW;
import static com.diabin.latte.core.net.HttpMethod.UPLOAD;

public final class RestClient {
    private final String URL;
    private final Map<String, Object> PARAMS;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;

    public RestClient(String URL,
                      Map<String, Object> PARAMS,
                      String DOWNLOAD_DIR,
                      String EXTENSION,
                      String NAME,
                      IRequest REQUEST,
                      ISuccess SUCCESS,
                      IError ERROR,
                      IFailure FAILURE,
                      RequestBody requestBody,
                      Context context,
                      LoaderStyle loaderStyle,
                      File FILE) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.BODY = requestBody;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = FILE;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();

        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW://要求body不为空，参数必须为空
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                MultipartBody.Part part =
                        MultipartBody.Part.
                                createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, part);
                break;
            default:
                break;

        }

        if (call != null) {
            call.enqueue(getRequestCallbacks());
        }
    }

    public final RequestCallbacks getRequestCallbacks() {
        return new RequestCallbacks(REQUEST, SUCCESS, ERROR, FAILURE, LOADER_STYLE);
    }

    public final RestClient get() {
        request(GET);
        return this;
    }

    public final RestClient post() {
        //post_raw body not null
        if (BODY == null) {
            request(POST);
        } else {
            //参数非空
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null");
            }
            request(POST_RAW);
        }
        return this;
    }

    public final RestClient put() {
        if (BODY == null) {
            request(PUT);
        } else {
            //参数非空
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null");
            }
            //参数要为空
            request(PUT_RAW);
        }
        return this;
    }

    public final RestClient delete() {
        request(DELETE);
        return this;
    }

    public final RestClient upload() {
        request(UPLOAD);
        return this;
    }
}
