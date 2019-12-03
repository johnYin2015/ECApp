package com.diabin.latte.core.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.core.R;
import com.diabin.latte.core.delegates.LatteDelegate;

/**
 * 每一个页面，Act中的
 * 作者：johnyin2015
 * 日期：2019/11/12 18:47
 */
public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {

    private long mExitTime = 0L;
    private static final long EXIT_TIME = 2000L;

    //重新获得焦点时，让rootView可以触摸
    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && keyCode == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
                Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                getProxyActivity().finish();
                if (mExitTime != 0) {
                    mExitTime = 0L;
                }
            }
            return true;//消费了，不会传给系统处理
        }
        return false;
    }
}
