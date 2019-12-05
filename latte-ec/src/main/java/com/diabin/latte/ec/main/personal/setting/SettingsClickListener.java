package com.diabin.latte.ec.main.personal.setting;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ec.main.personal.list.ListBean;

/**
 * Created by 傅令杰
 */

public class SettingsClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public SettingsClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //点击控制通知开关所在item，不做处理
                break;
            case 2:
                DELEGATE.getSupportDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
