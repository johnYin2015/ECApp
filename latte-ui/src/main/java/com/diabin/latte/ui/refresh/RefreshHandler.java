package com.diabin.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.ui.recycler.DataConverter;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;

/**
 * 作者：johnyin2015
 * 日期：2019/11/13 13:22
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final PagingBean BEAN;

    private RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                           RecyclerView recyclerview,
                           DataConverter converter,
                           PagingBean bean) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.RECYCLERVIEW = recyclerview;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT,
                                        RecyclerView recyclerview,
                                        DataConverter converter) {
        return new RefreshHandler(REFRESH_LAYOUT, recyclerview, converter, new PagingBean());
    }

    //刷新时回调
    @Override
    public void onRefresh() {
        refresh();
    }

    //2秒后停止刷新
    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        //全局通用的handler
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行网络请求，成功时停止刷新
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayd(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject jsonObject = JSON.parseObject(response);
                        BEAN.setTotalCount(jsonObject.getInteger("total"))
                                .setPageSize(jsonObject.getInteger("page_size"));
                       mAdapter= MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                       mAdapter.setOnLoadMoreListener(RefreshHandler.this,
                               RECYCLERVIEW);
                       RECYCLERVIEW.setAdapter(mAdapter);
                       BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }


    @Override
    public void onLoadMoreRequested() {

    }
}
