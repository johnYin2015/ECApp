package com.diabin.latte.core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * 作者：johnyin2015
 * 日期：2019/11/1 11:14
 */
public class Latte {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return (Handler) getConfigurations().get(ConfigKeys.HANDLER);
    }
}
