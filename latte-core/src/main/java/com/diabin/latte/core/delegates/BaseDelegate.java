package com.diabin.latte.core.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diabin.latte.core.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * BaseDelegate
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder unbinder;

    //子类重写此方法，返回一个布局资源
    public abstract Object setLayout();

    //绑定界面之后进行操作的方法
    public abstract void onBindView(View rootView,@Nullable Bundle savedInstanceState);

    //抽取出fragments的公共部分

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //根据子类返回的布局资源类型，生成rootView
        View rootView = null;
        if (setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer)setLayout(),container,false);
        }
        else if (setLayout() instanceof View){
            rootView = (View)setLayout();
        }

        //判空 判空 判空
        if (rootView != null) {
            unbinder = ButterKnife.bind(this,rootView);
            onBindView(rootView,savedInstanceState);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }
}
