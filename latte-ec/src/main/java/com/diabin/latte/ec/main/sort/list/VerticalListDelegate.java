package com.diabin.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.sort.SortDelegate;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * BindView字段可以在声明时设置默认值
 * 作者：johnyin2015
 * 日期：2019/11/15 20:55
 */
public class VerticalListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    public void initRecyclerView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //去掉动画效果
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list_data.json")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<MultipleItemEntity> data = new VerticalListDataConverter()
                                .setJsonData(response).convert();
                        final SortDelegate delegate = getParentDelegate();//sortDel
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data,delegate);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
