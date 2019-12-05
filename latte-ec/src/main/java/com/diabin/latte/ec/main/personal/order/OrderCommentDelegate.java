package com.diabin.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.util.callback.CallbackManager;
import com.diabin.latte.core.util.callback.CallbackType;
import com.diabin.latte.core.util.callback.IGlobalCallback;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ui.widget.AutoPhotoLayout;
import com.diabin.latte.ui.widget.StarLayout;

import butterknife.BindView;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/4 14:49
 */
public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout;

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
            @Override
            public void executeCallback(@NonNull Uri uri) {
                mAutoPhotoLayout.onCropTarget(uri);
            }
        });
    }
}
