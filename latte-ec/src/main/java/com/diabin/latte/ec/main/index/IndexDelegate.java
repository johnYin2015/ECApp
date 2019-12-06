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
import android.widget.Toast;

import com.diabin.latte.core.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.core.util.callback.CallbackManager;
import com.diabin.latte.core.util.callback.CallbackType;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.EcBottomDelegate;
import com.diabin.latte.ec.main.index.search.SearchDelegate;
import com.diabin.latte.ui.recycler.BaseItemDecoration;
import com.diabin.latte.ui.recycler.IndexDataConverter;
import com.diabin.latte.ui.refresh.RefreshHandler;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 * 作者：johnyin2015
 * 日期：2019/11/13 00:24
 */
public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

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

        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, args ->
                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show());

        mEtSearchView.setOnFocusChangeListener(this);
    }

    @OnClick(R2.id.icon_scan_index)
    public void onClickScanIcon(){
        startScanWithCheck(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            getParentDelegate().start(new SearchDelegate());
        }
    }
}
