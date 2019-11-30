package com.diabin.latte.core.app;


import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.diabin.latte.core.delegates.web.event.Event;
import com.diabin.latte.core.delegates.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 作者：johnyin2015
 * 日期：2019/11/1 13:15
 */
public class Configurator {
    //WeakHashMap内存不足，系统回收，而Configurator随着Application一起存在的
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();//存handler对象

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    public final Configurator withLoaderDelay(int delay) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAY,delay);
        return this;
    }

    public Configurator withJavaInterface(@NonNull String name) {
        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE,name);
        return this;
    }

    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager eventManager =EventManager.getInstance();
        eventManager.addEvent(name,event);
        return this;
    }

    //浏览器加载的host
    public Configurator withWebHost(@NonNull String host) {
        LATTE_CONFIGS.put(ConfigKeys.WEB_HOST,host);
        return this;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppid(String appid){
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID,appid);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret){
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET,appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity){
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY,activity);
        return this;
    }


    public void configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady)
            throw new RuntimeException("Configuration is not ready,call configure");
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfigurations(Enum<ConfigKeys> key){
        checkConfiguration();
        return (T)LATTE_CONFIGS.get(key);
    }


    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));//add a icon font
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }

    }
}
