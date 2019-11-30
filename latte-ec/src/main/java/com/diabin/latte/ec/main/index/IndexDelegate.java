package com.diabin.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.diabin.latte.core.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.core.ui.recycler.BaseItemDecoration;
import com.diabin.latte.core.ui.recycler.IndexDataConverter;
import com.diabin.latte.core.ui.refresh.RefreshHandler;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.EcBottomDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

/**
 * 首页
 * 作者：johnyin2015
 * 日期：2019/11/13 00:24
 */
public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar;
    @BindView(R2.id.icon_scan_index)
    IconTextView mScanIcon = null;
    @BindView(R2.id.index_search_view)
    AppCompatEditText mEtSearchView = null;

    private RefreshHandler mRefreshHandler = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        mRefreshLayout.setProgressViewOffset(true,120,300);
    }

    private void initRecyclerView(){
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(BaseItemDecoration.create(
                ContextCompat.getColor(getContext(),R.color.app_background),5));

        final EcBottomDelegate parentDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(parentDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
        mRefreshHandler.firstPage("index_data.json");
    }
}
