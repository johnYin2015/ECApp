package com.diabin.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ec.R;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 20:10
 */
public class GoodsDetailDelegate extends LatteDelegate {

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_good_detail;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
    }
}
