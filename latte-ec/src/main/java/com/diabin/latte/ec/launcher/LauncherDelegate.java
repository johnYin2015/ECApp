package com.diabin.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.diabin.latte.core.app.AccountManager;
import com.diabin.latte.core.app.IUserChecker;
import com.diabin.latte.core.ui.launcher.IOnLauncherFinishListener;
import com.diabin.latte.core.ui.launcher.OnLauncherFinishTag;
import com.diabin.latte.core.ui.launcher.ScrollLauncherTag;
import com.diabin.latte.core.ui.storage.LattePreference;
import com.diabin.latte.core.util.timer.BaseTimerTask;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.util.timer.ITimerListener;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

public class LauncherDelegate extends LatteDelegate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer;

    private Timer mTimer;
    private int mCount = 5;//5 4 3 2 1

    private IOnLauncherFinishListener mIOnLauncherFinishListener;

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        initTimer();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IOnLauncherFinishListener) {
            mIOnLauncherFinishListener = (IOnLauncherFinishListener) activity;
        }
    }

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    public void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask timerTask = new BaseTimerTask(this);
        mTimer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        mTimer.cancel();
                        mTimer = null;
                        checkIsShowScroll();
                    }
                }
            }
        });
    }

    //检查是否进入滑动启动页
    private void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name())) {
            //进入滑动启动页
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //检查用户是否已经登录app
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
