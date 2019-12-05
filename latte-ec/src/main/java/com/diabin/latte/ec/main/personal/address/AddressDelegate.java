package com.diabin.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ec.R;

/**
 * 描述：收货地址
 * 作者：johnyin2015
 * 日期：2019/12/4 04:08
 */
public class AddressDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
    }
}
