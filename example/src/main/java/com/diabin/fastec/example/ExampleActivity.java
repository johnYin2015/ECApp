package com.diabin.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.diabin.latte.core.activities.ProxyActivity;
import com.diabin.latte.core.app.Configurator;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.ui.launcher.IOnLauncherFinishListener;
import com.diabin.latte.ui.launcher.OnLauncherFinishTag;
import com.diabin.latte.ec.launcher.LauncherDelegate;
import com.diabin.latte.ec.main.EcBottomDelegate;
import com.diabin.latte.ec.sign.ISignListener;
import com.diabin.latte.ec.sign.SigninDelegate;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements ISignListener, IOnLauncherFinishListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Configurator.getInstance().withActivity(this);//全局act放入配置中

        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignupSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSigninSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    //传入状态
    @Override
    public void onLaunchFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                //Toast.makeText(this,"启动结束，用户登录了",Toast.LENGTH_LONG).show();
                getSupportDelegate().start(new EcBottomDelegate());
                break;
            case NOTSIGNED:
                Toast.makeText(this,"启动结束，用户没登录",Toast.LENGTH_LONG).show();
                getSupportDelegate().start(new SigninDelegate());
                break;
            default:
                break;
        }
    }
}
