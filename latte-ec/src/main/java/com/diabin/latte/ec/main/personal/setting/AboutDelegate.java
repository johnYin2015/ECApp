package com.diabin.latte.ec.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.ec.R;

/**
 * Created by 傅令杰
 */

public class AboutDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        final AppCompatTextView textView = $(R.id.tv_info);

        RestClient.builder()
                .url("about.json")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        textView.setText(info);
                    }
                })
                .build()
                .get();
    }

    /*@Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final AppCompatTextView textView = $(R.id.tv_info);

        RestClient.builder()
                .url("about.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        textView.setText(info);
                    }
                })
                .build()
                .get();
    }*/
}
