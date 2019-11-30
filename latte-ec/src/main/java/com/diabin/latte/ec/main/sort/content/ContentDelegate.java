package com.diabin.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 21:49
 */
public class ContentDelegate extends LatteDelegate {

    private static final String KEY_CONTENT_ID = "contentId";
    private int mContentId = -1;
    @BindView(R2.id.rv_section_content_container)
    RecyclerView mRecyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_content_list;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        final StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        initData();
    }

    private void initData() {

        RestClient.builder()
                .url("sort_content_data_1.json?contentId=" + mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<SectionBean> data =
                                new SectionDataConverter().convert(response);
                        final SectionAdapter adapter =
                                new SectionAdapter(R.layout.item_section_content,
                                        R.layout.item_section_header, data);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(KEY_CONTENT_ID, contentId);
        final ContentDelegate listDelegate = new ContentDelegate();
        listDelegate.setArguments(args);
        return listDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = getArguments().getInt(KEY_CONTENT_ID);
        }
    }
}
