package com.diabin.latte.core.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

//泛型定义在末尾
public class LauncherHolder implements Holder<Integer> {

    //每一页要显示的控件
    private AppCompatImageView mImageView;

    //返回要显示的view
    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    //滑动时更新
    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }

}
