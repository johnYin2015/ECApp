package com.diabin.latte.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.main.personal.PersonalDelegate;
import com.diabin.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/3 23:45
 */
public class OrderListDelegate extends LatteDelegate {

    private String mType = null;

    private RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args!=null){
            mType = args.getString(PersonalDelegate.ORDER_TYPE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        mRecyclerView = $(R.id.rv_order_list);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("order_list.json")
                .params("type", mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        //TODO rv addOnItemTouchListener
                    }
                })
                .build()
                .get();
    }
}
