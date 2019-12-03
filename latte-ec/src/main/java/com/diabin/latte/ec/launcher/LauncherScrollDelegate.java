package com.diabin.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.diabin.latte.core.app.AccountManager;
import com.diabin.latte.core.app.IUserChecker;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ui.launcher.IOnLauncherFinishListener;
import com.diabin.latte.ui.launcher.LauncherHolderCreator;
import com.diabin.latte.ui.launcher.OnLauncherFinishTag;
import com.diabin.latte.ui.launcher.ScrollLauncherTag;
import com.diabin.latte.core.ui.storage.LattePreference;
import com.diabin.latte.ec.R;

import java.util.ArrayList;

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    //页面翻转view
    private ConvenientBanner<Integer> mConvenientBanner;

    //图片集合
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private IOnLauncherFinishListener mIOnLauncherFinishListener;

    //初始化图片集合
    private void init() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);

        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getProxyActivity());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IOnLauncherFinishListener) {
            mIOnLauncherFinishListener = (IOnLauncherFinishListener) activity;
        }
    }

    @Override
    public void onItemClick(int position) {
        //如果是最后一张
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name(), true);
            //检查是否已登录
            AccountManager.checkAccount(new IUserChecker() {

                //登录
                @Override
                public void onSignin() {
                    if (mIOnLauncherFinishListener != null) {
                        mIOnLauncherFinishListener.onLaunchFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                //未登录
                @Override
                public void onNotSignin() {
                    if (mIOnLauncherFinishListener != null) {
                        mIOnLauncherFinishListener.onLaunchFinish(OnLauncherFinishTag.NOTSIGNED);
                    }
                }
            });
        }
    }
}
