package com.diabin.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.RestCreator;
import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.net.rx.RxRestClient;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);

        //testRestClientGet();
        //onRxCallGet();

        //icon_scan
    }

    private void onRxCallGet() {
        final String url = "index.jsp";
        final WeakHashMap<String,Object> params = new WeakHashMap<>();
        Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//观察到数据后
                .subscribe(new Observer<String>() {//定义观察到数据或事件触发时行为
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onRxRestClientCallGet() {
        final String url = "index.jsp";
        final WeakHashMap<String,Object> params = new WeakHashMap<>();
        Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        RxRestClient.builder()
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//观察到数据后
                .subscribe(new Observer<String>() {//定义观察到数据或事件触发时行为
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testRestClientGet() {
        RestClient restClient = RestClient.builder()
                .url("http://127.0.0.1/index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        Log.d("keke",response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError() {

                    }
                })
                .build()
                .get();
    }
}
