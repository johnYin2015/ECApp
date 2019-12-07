package com.diabin.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ui.recycler.ItemType;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleItemEntityBuilder;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public class ImageDelegate extends LatteDelegate {

    private RecyclerView mRecyclerView = null;

    private static final String ARG_PICTURES = "ARG_PICTURES";

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    private void initImages() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            final ArrayList<String> pictures = arguments.getStringArrayList(ARG_PICTURES);//StringArrayList frag支持
            final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
            final int size;//初始size
            if (pictures != null) {
                size = pictures.size();
                for (int i = 0; i < size; i++) {
                    final String imageUrl = pictures.get(i);
                    final MultipleItemEntity entity = MultipleItemEntityBuilder
                            .builder()
                            .setItemType(ItemType.SINGLE_BIG_IMAGE)
                            .setField(MultipleFields.IMAGE_URL, imageUrl)
                            .build();
                    entities.add(entity);
                }
                final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
                mRecyclerView.setAdapter(adapter);
            }
        }
    }

    public static ImageDelegate create(ArrayList<String> pictures) {
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES, pictures);
        final ImageDelegate delegate = new ImageDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_image_container);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }

}
