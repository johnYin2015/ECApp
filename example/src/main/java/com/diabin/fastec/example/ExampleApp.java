package com.diabin.fastec.example;

import android.app.Application;
import android.support.annotation.Nullable;

import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.delegates.web.event.TestEvent;
import com.diabin.latte.core.net.interceptors.DebugInterceptor;
import com.diabin.latte.core.net.rx.AddCookieInterceptor;
import com.diabin.latte.core.util.callback.CallbackManager;
import com.diabin.latte.core.util.callback.CallbackType;
import com.diabin.latte.core.util.callback.IGlobalCallback;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.icon.FontEcModule;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：johnyin2015
 * 日期：2019/11/1 09:08
 * 存储全局信息
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection SpellCheckingInspection
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelay(1000)
                //.withApiHost("http://127.0.0.1/")
                .withApiHost("http://mock.fulingjie.com/mock-android/data/")
                //.withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withInterceptor(new DebugInterceptor("haha", R.raw.test))
                .withWeChatAppid("")
                .withWeChatAppSecret("")
                .withJavaInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebHost("https://www.baidu.com")
                .withInterceptor(new AddCookieInterceptor())//添加cookie同步浏览器
                .configure();
        //验证模块之间的间接依赖


        //初始化dao
        DatabaseManager.getInstance().init(this);

        initStetho();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }

    private void initStetho() {

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }
}
